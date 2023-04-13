package id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.repository.AppRegistration.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppRegistrationServiceImpl implements AppRegistrationService{
    private final Storage storage;
    private AppDataValidator appDataValidator = new AppDataValidator();
    private final AppDataRepository appDataRepository;

    @Override
    public boolean validateApp(AppDataRequest appDataRequest) throws IOException {
        AppData appData = create(appDataRequest);
        boolean isAppDataValid = appDataValidator.validate(appData);
        if (isAppDataValid){
            appDataRepository.save(appData);
        }
        return isAppDataValid;
    }

    public List<AppData> getAllAppData(){
        return appDataRepository.findAll();
    }

    public String storeFile(MultipartFile file) throws IOException {
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        String fileId = "x";
        String path = "/file/" + fileId;
        storage.uploadFile(inputStream, path);
        return path;
    }

    public AppData create(AppDataRequest appDataRequest) throws IOException {
        return AppData.builder()
                .name(appDataRequest.getAppName())
                .imageUrl(storeFile(appDataRequest.getImageFile()))
                .installerUrl(storeFile(appDataRequest.getInstallerFile()))
                .description(appDataRequest.getDescription())
                .version(appDataRequest.getVersion())
                .price(appDataRequest.getPrice())
                .build();
    }



}
