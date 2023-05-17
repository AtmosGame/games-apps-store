package id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AppProfileUpdate {
    private String appName;
    private String description;
    private Double price;
    public AppProfileUpdate(String appName, String description, Double price) {
        this.appName = appName;
        this.description = description;
        this.price = price;
    }
}
