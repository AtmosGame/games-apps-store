package id.ac.ui.cs.advprog.gamesappsstore.dto.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserDetailsResponseTest {
    @Test
    void getTest() {
        UserDetailsResponse response = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        Assertions.assertEquals(2, response.getId());
        Assertions.assertEquals("ehees", response.getUsername());
        Assertions.assertEquals("admin", response.getRole());
        Assertions.assertEquals("bio", response.getBio());
        Assertions.assertEquals("applications", response.getApplications());
        Assertions.assertEquals(true, response.getActive());
        Assertions.assertEquals("https://google.com", response.getProfilePicture());
    }

    @Test
    void equalsTest() {
        UserDetailsResponse response1 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        UserDetailsResponse response2 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        UserDetailsResponse response3 = UserDetailsResponse.builder()
                .id(3)
                .username("x")
                .role("user")
                .bio("bios")
                .applications("epps")
                .active(false)
                .profilePicture("https://google.com/")
                .build();
        Assertions.assertEquals(response2, response1);
        Assertions.assertNotEquals(response3, response1);
        Assertions.assertTrue(response1.equals(response2) && response2.equals(response1));

    }

    @Test
    void hashCodeTest() {
        UserDetailsResponse response1 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        UserDetailsResponse response2 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        UserDetailsResponse response3 = UserDetailsResponse.builder()
                .id(3)
                .username("x")
                .role("user")
                .bio("bios")
                .applications("epps")
                .active(false)
                .profilePicture("https://google.com/")
                .build();
        Assertions.assertEquals(response1.hashCode(), response2.hashCode());
        Assertions.assertNotEquals(response1.hashCode(), response3.hashCode());
    }

    @Test
    void setValidTest() {
        UserDetailsResponse response1 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        UserDetailsResponse response2 = UserDetailsResponse.builder()
                .id(3)
                .username("x")
                .role("user")
                .bio("bios")
                .applications("epps")
                .active(false)
                .profilePicture("https://google.com/")
                .build();
        response2.setId(response1.getId());
        response2.setUsername(response1.getUsername());
        response2.setRole(response1.getRole());
        response2.setBio(response1.getBio());
        response2.setApplications(response1.getApplications());
        response2.setActive(response1.getActive());
        response2.setProfilePicture(response1.getProfilePicture());
        Assertions.assertEquals(response1, response2);
    }

    @Test
    void setNullTest() {
        var builder = UserDetailsResponse.builder();
        Assertions.assertThrows(NullPointerException.class, () -> {
            builder.id(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            builder.active(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            builder.username(null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            builder.role(null);
        });
    }

    @Test
    void builderToStringTest() {
        String expected = "UserDetailsResponse.UserDetailsResponseBuilder("
            + "id=3, username=x, role=user, profilePicture=https://google.com/, "
            +"bio=bios, applications=epps, active=false)";
        var response2 = UserDetailsResponse.builder()
                .id(3)
                .username("x")
                .role("user")
                .bio("bios")
                .applications("epps")
                .active(false)
                .profilePicture("https://google.com/");
        Assertions.assertEquals(expected, response2.toString());
    }

    @Test
    void toStringTest() {
        String expected = "UserDetailsResponse("
                + "id=3, username=x, role=user, profilePicture=https://google.com/, "
                +"bio=bios, applications=epps, active=false)";
        var user = UserDetailsResponse.builder()
                .id(3)
                .username("x")
                .role("user")
                .bio("bios")
                .applications("epps")
                .active(false)
                .profilePicture("https://google.com/")
                .build();
        Assertions.assertEquals(expected, user.toString());
    }
}
