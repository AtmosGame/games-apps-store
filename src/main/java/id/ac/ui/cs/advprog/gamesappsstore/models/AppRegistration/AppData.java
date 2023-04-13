package id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration;


import id.ac.ui.cs.advprog.gamesappsstore.models.verification.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppData {
    private String id;
    private String name;
    private String description;
    private MultipartFile imageFile;

    private MultipartFile installerFile;
    private String version;
    private Double price;
    private VerificationStatus verificationStatus;
    private Integer verificationAdminId;
    private Date verificationDate;
}
