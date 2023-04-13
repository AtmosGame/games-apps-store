package id.ac.ui.cs.advprog.gamesappsstore.models.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
public class Verification {
//    @Id
//    @GeneratedValue
    private Integer id;
//    @OneToOne
//    @JoinColumn(name = "_app_id", nullable = false)
    private AppData app;
    private Integer adminId;
    private Date date;
//    @Enumerated(EnumType.STRING)
    private VerificationStatus status;
}
