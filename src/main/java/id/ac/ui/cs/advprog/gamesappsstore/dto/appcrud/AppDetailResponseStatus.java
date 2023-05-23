package id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class AppDetailResponseStatus {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String version;
    private Double price;
    private VerificationStatus verificationStatus;
}
