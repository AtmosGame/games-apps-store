package id.ac.ui.cs.advprog.gamesappsstore.dto.verification;

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
}
