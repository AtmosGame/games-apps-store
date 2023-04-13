package id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppRegistrationService {

    boolean validateApp(AppDataRequest appDataRequest) throws IOException;

    String storeFile(MultipartFile file) throws IOException;
    AppData create(AppDataRequest appDataRequest) throws IOException;
}
