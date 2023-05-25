package id.ac.ui.cs.advprog.gamesappsstore.dto.notfication;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrodcastRequest {
    private Long appDevId;
    private String message;
}
