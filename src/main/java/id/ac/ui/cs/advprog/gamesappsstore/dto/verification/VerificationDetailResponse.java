package id.ac.ui.cs.advprog.gamesappsstore.dto.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class VerificationDetailResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String installerUrl;
    private String version;
    private Double price;

    private String verificationStatus;
    private Integer verificationAdminId;
    private Date verificationDate;

    public VerificationDetailResponse(AppData appData) {
        this.id = appData.getId();
        this.name = appData.getName();
        this.imageUrl = appData.getImageUrl();
        this.description = appData.getDescription();
        this.installerUrl = appData.getInstallerUrl();
        this.version = appData.getVersion();
        this.price = appData.getPrice();
        this.verificationStatus = appData.getVerificationStatus().toString();
        this.verificationAdminId = appData.getVerificationAdminId();
        this.verificationDate = appData.getVerificationDate();
    }
}
