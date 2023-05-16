package id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
public class AppInstallerUpdate {
    private MultipartFile installerFile;
    private String version;
    public AppInstallerUpdate(MultipartFile installerFile, String version) {
        this.installerFile = installerFile;
        this.version = version;
    }

}
