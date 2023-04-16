package id.ac.ui.cs.advprog.gamesappsstore.dto;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AppDetailResponse {
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
