package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDataRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppRegistrationService {
    boolean validateApp(AppDataRequest appDataRequest) throws IOException;
    String storeFile(MultipartFile file) throws IOException;
    AppData create(AppDataRequest appDataRequest) throws IOException;
    List<AppData> findAllVerifiedApps();
    List<AppData> findAllUnverifiedApps();
}
