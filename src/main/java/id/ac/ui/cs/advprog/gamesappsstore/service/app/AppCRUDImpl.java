package id.ac.ui.cs.advprog.gamesappsstore.service.app;
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
    public AppData create(long userId, AppDataRequest appDataRequest) throws IOException {
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
    public AppData updateProfile(Long id, AppProfileUpdate appProfileUpdate) throws IOException{
        AppData appData = findById(id);
        appData.setDescription(appProfileUpdate.getDescription());
        appData.setName(appProfileUpdate.getAppName());
        appData.setPrice(appProfileUpdate.getPrice());

        appDataValidator.validate(appData);
        return appDataRepository.save(appData);
    }

    @Override
    public AppData updateInstaller(Long id, AppInstallerUpdate appInstallerUpdate) throws IOException{
        if(isAppDoesNotExist(id)){
            throw new AppDataDoesNotExistException();
        }
        else{
            AppData appData = appDataRepository.findById(id).get();
            String bfrVersion = appData.getVersion();
            appData.setInstallerUrl(storeFile(appInstallerUpdate.getInstallerFile()));
            appData.setVersion(appInstallerUpdate.getVersion());

            appIntallerValidator.validate(appData, bfrVersion);
            return appDataRepository.save(appData);
        }
    }

    @Override
    public AppData updateImage(Long id, AppImageUpdate appImageUpdate) throws IOException{
        if(isAppDoesNotExist(id)){
            throw new AppDataDoesNotExistException();
        }
        else{
            AppData appData = appDataRepository.findById(id).get();
            appData.setImageUrl(storeFile(appImageUpdate.getImageFile()));
            appDataValidator.validate(appData);
            return appDataRepository.save(appData);
        }
    }
    @Override
    public AppData findById(Long id) throws IOException{
        Optional<AppData> appData = appDataRepository.findById(id);
        if(isAppDoesNotExist(id)) {
            throw new AppDataDoesNotExistException();
        }
        else{
            return appData.get();
        }
    }

    @Override
    public void delete(Long id)throws IOException{
        if(isAppDoesNotExist(id)){
            throw new AppDataDoesNotExistException();
        }
        else{
            appDataRepository.deleteById(String.valueOf(id));
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

    private boolean isAppDoesNotExist(Long id) {
        return appDataRepository.findById(id).isEmpty();
    }
}
