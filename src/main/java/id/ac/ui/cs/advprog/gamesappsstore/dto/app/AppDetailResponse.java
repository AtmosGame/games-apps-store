package id.ac.ui.cs.advprog.gamesappsstore.dto.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppDetailResponse {
    private Long id;
    private String name;
    private Integer userId;
    private String imageUrl;
    private String description;
    private String version;
    private Double price;
    private VerificationStatus verificationStatus;

    public AppDetailResponse(AppData appData) {
        this.id = appData.getId();
        this.name = appData.getName();
        this.userId = appData.getUserId();
        this.imageUrl = appData.getImageUrl();
        this.description = appData.getDescription();
        this.version = appData.getVersion();
        this.price = appData.getPrice();
        this.verificationStatus = appData.getVerificationStatus();
    }
}
