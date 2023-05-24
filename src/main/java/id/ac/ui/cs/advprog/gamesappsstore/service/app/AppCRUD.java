package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.*;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AppCRUD {
    String storeFile(MultipartFile file) throws IOException;
    AppData create(Integer userId, AppDataRequest appDataRequest) throws IOException;
    AppData updateProfile(Long id, AppProfileUpdate appProfileUpdate, Integer userId) throws IOException;
    AppData updateInstaller(Long id, AppInstallerUpdate appInstallerUpdate, Integer userId) throws IOException;
    AppData updateImage(Long id, AppImageUpdate appImageUpdate, Integer userId) throws IOException;
    void delete(Long id, Integer userId) throws IOException;
    AppData findById(Long id) throws IOException;
    List<AppDetailResponseStatus> findAllApp();

}
