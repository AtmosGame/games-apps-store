package id.ac.ui.cs.advprog.gamesappsstore.models.user;

import id.ac.ui.cs.advprog.gamesappsstore.models.user.enums.UserRole;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private UserRole role;
    private String profilePicture;
    private Boolean active;

    public User(Integer id, UserRole role) {
        this.id = id;
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.equals(UserRole.ADMIN);
    }

    public boolean isUser() {
        return this.role.equals(UserRole.USER);
    }

    public boolean isDeveloper() {
        return this.role.equals(UserRole.DEVELOPER);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.equals(UserRole.ADMIN)) {
            return UserRole.ADMIN.getGrantedAuthority();
        } else if (role.equals(UserRole.DEVELOPER)) {
            return UserRole.DEVELOPER.getGrantedAuthority();
        } else {
            return UserRole.USER.getGrantedAuthority();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
