package id.ac.ui.cs.advprog.gamesappsstore.models.user;

import id.ac.ui.cs.advprog.gamesappsstore.models.user.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Integer id;
    private UserRole role;

    public User(Integer id, UserRole role) {
        this.id = id;
        this.role = role;
    }

    public boolean isAdmin() {
        return this.role.equals(UserRole.ADMINISTRATOR);
    }

    public boolean isCustomer() {
        return this.role.equals(UserRole.CUSTOMER);
    }

    public boolean isDeveloper() {
        return this.role.equals(UserRole.DEVELOPER);
    }
}
