package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.core.download.api.IsAppOwnedAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.dto.download.AppDownloadResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppDownloadServiceImpl implements AppDownloadService {
    private final AppDataRepository appDataRepository;
    @Autowired
    private IsAppOwnedAPICall isAppOwnedAPICall;

    @Override
    public AppDownloadResponse getDownloadUrl(String username, Long id, String jwtToken){
        var appData = findById(id);
        if (appData.getPrice() == 0 || checkAppOwnership(username, id, jwtToken)){
            return new AppDownloadResponse(appData.getInstallerUrl());
        }
        return new AppDownloadResponse("you don't own the app");
    }

    private boolean checkAppOwnership(String username, Long id, String jwtToken){
        return isAppOwnedAPICall.execute(username, id, jwtToken);
    }

    private AppData findById(Long id) {
        Optional<AppData> appData = appDataRepository.findById(id);
        if(appData.isEmpty()) {
            throw new AppDataDoesNotExistException();
        }
        else{
            return appData.get();
        }
    }
}
