package id.ac.ui.cs.advprog.gamesappsstore.service;

import id.ac.ui.cs.advprog.gamesappsstore.models.*;
import id.ac.ui.cs.advprog.gamesappsstore.repository.AppDataRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class AppRegistrationServiceImpl implements AppRegistrationService{
    @Autowired
    AppDataValidator appDataValidator;
    AppDataRepositoryImpl appDataRepository;
    public boolean validateApp(AppData appData){
        boolean isAppDataValid = appDataValidator.validate(appData);
        if (isAppDataValid){
            appDataRepository.addAppData(appData);
        }
        return isAppDataValid;
    }
}
