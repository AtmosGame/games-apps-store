package id.ac.ui.cs.advprog.gamesappsstore.service.appregistration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppInstallerRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface AppInstallerUpdate {
    boolean validateApp(Integer id, AppInstallerRequest AppInstallerRequest) throws IOException;
    String storeFile(MultipartFile file) throws IOException;
    AppData updateInstaller(Integer id, AppInstallerRequest appInstallerRequest) throws IOException;
}
