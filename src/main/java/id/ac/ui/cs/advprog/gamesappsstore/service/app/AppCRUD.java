package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appCRUD.AppProfileUpdate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppCRUD {
    String storeFile(MultipartFile file) throws IOException;
    AppData create(AppDataRequest appDataRequest) throws IOException;
    AppData updateProfile(Long id, AppProfileUpdate appProfileUpdate) throws IOException;
    AppData updateInstaller(Long id, AppInstallerUpdate appInstallerUpdate) throws IOException;
    AppData updateImage(Long id, AppImageUpdate appImageUpdate) throws IOException;
    void delete(Long id) throws IOException;
    AppData findById(Long id) throws IOException;

    List<AppData> findAllVerifiedApps();
    List<AppData> findAllUnverifiedApps();
}