package id.ac.ui.cs.advprog.gamesappsstore.models;

import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserTest {
    @Test
    void isAdminTest() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(true)
                .role(UserRole.ADMIN)
                .build();

        Assertions.assertTrue(user.isAdmin());
        Assertions.assertFalse(user.isUser());
        Assertions.assertFalse(user.isDeveloper());
        Assertions.assertEquals(UserRole.ADMIN.getGrantedAuthority(), user.getAuthorities());
    }

    @Test
    void isUserTest() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(true)
                .role(UserRole.USER)
                .build();

        Assertions.assertFalse(user.isAdmin());
        Assertions.assertTrue(user.isUser());
        Assertions.assertFalse(user.isDeveloper());
        Assertions.assertEquals(UserRole.USER.getGrantedAuthority(), user.getAuthorities());
    }

    @Test
    void isDeveloperTest() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(true)
                .role(UserRole.DEVELOPER)
                .build();

        Assertions.assertFalse(user.isAdmin());
        Assertions.assertFalse(user.isUser());
        Assertions.assertTrue(user.isDeveloper());
        Assertions.assertEquals(UserRole.DEVELOPER.getGrantedAuthority(), user.getAuthorities());
    }

    @Test
    void passwordIsNull() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(true)
                .role(UserRole.DEVELOPER)
                .build();

        Assertions.assertNull(user.getPassword());
    }

    @Test
    void accountActiveTest() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(true)
                .role(UserRole.DEVELOPER)
                .build();

        Assertions.assertTrue(user.isAccountNonExpired());
        Assertions.assertTrue(user.isAccountNonLocked());
        Assertions.assertTrue(user.isCredentialsNonExpired());
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    void accountInactiveTest() {
        User user = User.builder()
                .id(1)
                .username("uwooghsneggs")
                .active(false)
                .role(UserRole.DEVELOPER)
                .build();

        Assertions.assertFalse(user.isAccountNonExpired());
        Assertions.assertFalse(user.isAccountNonLocked());
        Assertions.assertFalse(user.isCredentialsNonExpired());
        Assertions.assertFalse(user.isEnabled());
    }
}
