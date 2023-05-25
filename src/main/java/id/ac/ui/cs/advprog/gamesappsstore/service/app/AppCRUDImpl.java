package id.ac.ui.cs.advprog.gamesappsstore.service.app;
import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.*;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.AppDevDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppInstallerValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.EmptyFormException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.AppDeveloperRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AppCRUDImpl implements AppCRUD {
    private final Storage storage;
    private AppDataValidator appDataValidator = new AppDataValidator();
    private AppInstallerValidator appInstallerValidator = new AppInstallerValidator();
    private final AppDataRepository appDataRepository;
    private final AppDeveloperRepository appDeveloperRepository;
    private final NotificationService notificationService;
    public List<AppData> getAllAppData(){
        return appDataRepository.findAll();
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new EmptyFormException("file");
        }
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        String fileId = file.getOriginalFilename();
        String path = "/file/" + fileId;
        return storage.uploadFile(inputStream, path);
    }

    @Override
    public List<AppDetailResponseStatus> findAllApp(){
        List<AppData> appDataList = appDataRepository.findAll();
        return appDataList.stream()
                .map(this::createAppDetailResponseStatus)
                .toList();
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
        AppData ret = appDataRepository.save(appData);
        AppDev appDev = AppDev.builder()
                .id(ret.getId())
                .appId(appData.getId())
                .build();
        appDeveloperRepository.save(appDev);
        return ret;
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
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        AppData appData = findById(id);
        checkUserAuthorization(appData, userId);

        String bfrVersion = appData.getVersion();
        appData.setInstallerUrl(storeFile(appInstallerUpdate.getInstallerFile()));
        appData.setVersion(appInstallerUpdate.getVersion());

        appInstallerValidator.validate(appData, bfrVersion);
        Optional<AppDev> appDev = appDeveloperRepository.findByAppId(id);
        if(appDev.isPresent()){
            Long appDevId = appDev.get().getId();
            String message = String.format("Aplikasi %s melakukan pembaruan menjadi versi %s", appData.getName(), appData.getVersion());

            CompletableFuture.runAsync(() -> {
                try {
                    notificationService.handleNewBroadcast(appDevId, message);
                } catch (Exception e) {
                    // Handle exception
                    e.printStackTrace();
                }
            }, executorService);
        }
        else{
            throw new AppDevDoesNotExistException();
        }
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

    private void checkUserAuthorization(AppData app, Integer userId) {
        if (!app.getUserId().equals(userId)) {
            throw new UnauthorizedException("User is not authorized");
        }
    }

    public AppDetailResponseStatus createAppDetailResponseStatus(AppData appData){
        return AppDetailResponseStatus.builder()
                .id(appData.getId())
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .verificationStatus(appData.getVerificationStatus())
                .build();

    }
}
