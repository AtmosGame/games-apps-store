package id.ac.ui.cs.advprog.gamesappsstore.dto.apps;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppDetailResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private String description;
    private String version;
    private Double price;
}
