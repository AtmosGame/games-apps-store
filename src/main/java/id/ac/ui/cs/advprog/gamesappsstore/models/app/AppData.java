package id.ac.ui.cs.advprog.gamesappsstore.models.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppData {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer userId;
    private String imageUrl;
    private String description;
    private String installerUrl;
    private String version;
    private Double price;

    private VerificationStatus verificationStatus;
    private Integer verificationAdminId;
    private Date verificationDate;
}

