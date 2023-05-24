package id.ac.ui.cs.advprog.gamesappsstore.dto.app;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class AppDetailValidationResponse {
    private Boolean isValid;
}
