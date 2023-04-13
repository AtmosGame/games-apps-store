package id.ac.ui.cs.advprog.gamesappsstore.core.app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String imageUrl;
    private String description;
    private String installerUrl;
    private String version;
    private Double price;
}

