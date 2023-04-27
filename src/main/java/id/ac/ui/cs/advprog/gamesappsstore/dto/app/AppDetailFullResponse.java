package id.ac.ui.cs.advprog.gamesappsstore.dto.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppDetailFullResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String installerUrl;
    private String version;
    private Double price;
    private VerificationStatus verificationStatus;
    private Integer verificationAdminId;
    private Date verificationDate;
}
