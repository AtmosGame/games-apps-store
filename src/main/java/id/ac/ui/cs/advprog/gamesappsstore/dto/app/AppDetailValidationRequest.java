package id.ac.ui.cs.advprog.gamesappsstore.dto.app;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class AppDetailValidationRequest {
    private Long id;
    private String name;
    private Double price;
}
