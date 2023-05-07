package id.ac.ui.cs.advprog.gamesappsstore.dto.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsResponse {
    private Integer id;
    private String username;
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
    private Boolean active;
}
