package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppInstallerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AppInstallerUpdate {
    boolean validateApp(Integer id, AppInstallerRequest AppInstallerRequest) throws IOException;
    String storeFile(MultipartFile file) throws IOException;
    AppData updateInstaller(Integer id, AppInstallerRequest appInstallerRequest) throws IOException;
}
