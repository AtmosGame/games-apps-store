package id.ac.ui.cs.advprog.gamesappsstore.dto.app;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class AppInstallerRequest {
    private MultipartFile installerFile;
    private String version;
    public AppInstallerRequest(MultipartFile installerFile, String version) {
        this.installerFile = installerFile;
        this.version = version;
    }

}
