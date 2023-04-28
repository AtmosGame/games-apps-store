package id.ac.ui.cs.advprog.gamesappsstore.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
public class UserDetailsResponse {
    @NonNull
    private Integer id;
    @NonNull
    private String username;
    @NonNull
    private String role;
    private String profilePicture;
    private String bio;
    private String applications;
    @NonNull
    private Boolean active;
}
