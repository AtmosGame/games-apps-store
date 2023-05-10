package id.ac.ui.cs.advprog.gamesappsstore.service.app;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppIntallerValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppProfileUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.EmptyFormException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppCRUDImpl implements AppCRUD {
    private final Storage storage;
    private AppDataValidator appDataValidator = new AppDataValidator();
    private AppIntallerValidator appIntallerValidator = new AppIntallerValidator();
    private final AppDataRepository appDataRepository;

    public List<AppData> getAllAppData(){
        return appDataRepository.findAll();
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if(file == null){
            throw new EmptyFormException("file");
        }
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        String fileId = file.getOriginalFilename();
        String path = "/file/" + fileId;
        String test = storage.uploadFile(inputStream, path);
        return test;
    }

    @Override
    public AppData create(Integer userId, AppDataRequest appDataRequest) throws IOException {
        var appData = AppData.builder()
                .userId(userId)
                .name(appDataRequest.getAppName())
                .imageUrl(storeFile(appDataRequest.getImageFile()))
                .installerUrl(storeFile(appDataRequest.getInstallerFile()))
                .description(appDataRequest.getDescription())
                .version(appDataRequest.getVersion())
                .price(appDataRequest.getPrice())
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .build();

        appDataValidator.validate(appData);
        return appDataRepository.save(appData);
    }

    @Override
    public AppData updateProfile(Long id, AppProfileUpdate appProfileUpdate, Integer userId) throws IOException{
        AppData appData = findById(id);
        checkUserAuthorization(appData, userId);

        appData.setDescription(appProfileUpdate.getDescription());
        appData.setName(appProfileUpdate.getAppName());
        appData.setPrice(appProfileUpdate.getPrice());

        appDataValidator.validate(appData);
        return appDataRepository.save(appData);
    }

    @Override
    public AppData updateInstaller(Long id, AppInstallerUpdate appInstallerUpdate, Integer userId) throws IOException {
        AppData appData = findById(id);
        checkUserAuthorization(appData, userId);

        String bfrVersion = appData.getVersion();
        appData.setInstallerUrl(storeFile(appInstallerUpdate.getInstallerFile()));
        appData.setVersion(appInstallerUpdate.getVersion());

        appIntallerValidator.validate(appData, bfrVersion);
        return appDataRepository.save(appData);
    }

    @Override
    public AppData updateImage(Long id, AppImageUpdate appImageUpdate, Integer userId) throws IOException{
        AppData appData = findById(id);
        checkUserAuthorization(appData, userId);

        appData.setImageUrl(storeFile(appImageUpdate.getImageFile()));
        appDataValidator.validate(appData);
        return appDataRepository.save(appData);
    }

    @Override
    public void delete(Long id, Integer userId) throws IOException {
        AppData appData = findById(id);
        checkUserAuthorization(appData, userId);
        appDataRepository.deleteById(id);
    }

    @Override
    public AppData findById(Long id) throws IOException {
        Optional<AppData> appData = appDataRepository.findById(id);
        if(appData.isEmpty()) {
            throw new AppDataDoesNotExistException();
        } else {
            return appData.get();
        }
    }

    @Override
    public List<AppData> findAllVerifiedApps() {
        return appDataRepository.findByVerificationStatus(VerificationStatus.VERIFIED);
    }

    @Override
    public List<AppData> findAllUnverifiedApps() {
        return appDataRepository.findByVerificationStatusIsNull();
    }

    private void checkUserAuthorization(AppData app, Integer userId) {
        if (!app.getUserId().equals(userId)) {
            throw new UnauthorizedException("User is not authorized");
        }
    }

    private boolean isAppDoesNotExist(Long id) {
        return appDataRepository.findById(id).isEmpty();
    }
}
