package id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@Builder
public class AppDataRequest {
    private String appName;
    private MultipartFile imageFile;
    private String description;
    private MultipartFile installerFile;
    private String version;
    private Double price;
    public AppDataRequest(String appName, MultipartFile imageFile, String description, MultipartFile installerFile, String version, Double price) {
        this.appName = appName;
        this.imageFile = imageFile;
        this.description = description;
        this.installerFile = installerFile;
        this.version = version;
        this.price = price;
    }
}
