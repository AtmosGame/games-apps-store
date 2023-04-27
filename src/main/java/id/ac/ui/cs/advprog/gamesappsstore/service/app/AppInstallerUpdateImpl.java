package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppInstallerValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppInstallerRequest;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppInstallerUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppInstallerUpdateImpl implements AppInstallerUpdate {
    private final Storage storage;
    private AppInstallerValidator appInstallerValidator = new AppInstallerValidator();
    private final AppDataRepository appDataRepository;
    private AppDataValidator appDataValidator = new AppDataValidator();

    @Override
    public boolean validateApp(Integer id, AppInstallerRequest appInstallerRequest) throws IOException {
        AppData appData = updateInstaller(id, appInstallerRequest);
        boolean isAppDataValid = appDataValidator.validate(appData);
        if (isAppDataValid){
            appDataRepository.save(appData);
        }
        return isAppDataValid;
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        InputStream inputStream =  new BufferedInputStream(file.getInputStream());
        String fileId = "x";
        String path = "/file/" + fileId;
        storage.uploadFile(inputStream, path);
        return path;
    }

    @Override
    public AppData updateInstaller(Integer id, AppInstallerRequest appInstallerRequest) throws IOException {
        Optional<AppData> appData = appDataRepository.findById(Long.valueOf(id));
        if(appData == null){
            throw new IOException();
        }
        else{
            if(isUpdatedVersion(appData.get().getVersion(), appInstallerRequest.getVersion())){
                return AppData.builder()
                        .id(Long.valueOf(id))
                        .name(appData.get().getName())
                        .imageUrl(appData.get().getImageUrl())
                        .installerUrl(storeFile(appInstallerRequest.getInstallerFile()))
                        .description(appData.get().getDescription())
                        .version(appInstallerRequest.getVersion())
                        .price(appData.get().getPrice())
                        .build();
            }
            else{
                throw new IOException();
            }
        }
    }

    public boolean isUpdatedVersion(String currentVersion, String beforeVersion){
        ArrayList<Integer> versionNumber = new ArrayList<>();
        ArrayList<Integer> beforeVersionNumber = new ArrayList<>();

        int currentNumber = 0;
        for(int i = 0; i < currentVersion.length(); i++){
            if(currentVersion.charAt(i) == '.'){
                currentNumber = 0;
                versionNumber.add(currentNumber);
            }
            else{
                char currentChar = currentVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }

        currentNumber = 0;
        for(int i = 0; i < beforeVersion.length(); i++){
            if(beforeVersion.charAt(i) == '.'){
                currentNumber = 0;
                beforeVersionNumber.add(currentNumber);
            }
            else{
                char currentChar = beforeVersion.charAt(i);
                currentNumber *= 10;
                currentNumber += currentChar - '0';
            }
        }

        Boolean isValid = true;
        if(versionNumber.size() != beforeVersionNumber.size()){
            isValid = false;
        }
        for(int i = 0; i < versionNumber.size(); i++){
            if(isValid == false) break;
            if(versionNumber.get(i) == beforeVersionNumber.get(i)){
                continue;
            }
            else if(versionNumber.get(i) < beforeVersionNumber.get(i)){
                isValid = false;
            }
            else{
                break;
            }
        }
        return isValid;
    }


}
