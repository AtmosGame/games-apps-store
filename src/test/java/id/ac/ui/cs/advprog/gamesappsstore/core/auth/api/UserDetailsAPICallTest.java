package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.auth.UserDetailsResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsAPICallTest {
    private final String jwtToken = "Bearer aisojasoifjas.dsfsdjifsdofj";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserDetailsAPICall userDetailsAPICall = new UserDetailsAPICall();

    @Test
    void whenUserUserTest() {
        UserDetailsResponse expectedResponse3 = UserDetailsResponse.builder()
                .id(3)
                .username("eheez")
                .role("user")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        User user3 = User.builder()
                .id(3)
                .username("eheez")
                .role(UserRole.USER)
                .active(true)
                .profilePicture("https://google.com")
                .build();
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserDetailsResponse.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse3, HttpStatusCode.valueOf(200)));
        User user = userDetailsAPICall.execute(jwtToken);
        Assertions.assertEquals(user3, user);
    }

    @Test
    void whenUserDeveloperTest() {
        UserDetailsResponse expectedResponse1 = UserDetailsResponse.builder()
                .id(1)
                .username("ehee")
                .role("developer")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        User user1 = User.builder()
                .id(1)
                .username("ehee")
                .role(UserRole.DEVELOPER)
                .active(true)
                .profilePicture("https://google.com")
                .build();
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserDetailsResponse.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse1, HttpStatusCode.valueOf(200)));
        User user = userDetailsAPICall.execute(jwtToken);
        Assertions.assertEquals(user1, user);
    }

    @Test
    void whenUserAdminTest() {
        UserDetailsResponse expectedResponse2 = UserDetailsResponse.builder()
                .id(2)
                .username("ehees")
                .role("admin")
                .bio("bio")
                .applications("applications")
                .active(true)
                .profilePicture("https://google.com")
                .build();
        User user2 = User.builder()
                .id(2)
                .username("ehees")
                .role(UserRole.ADMIN)
                .active(true)
                .profilePicture("https://google.com")
                .build();
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(UserDetailsResponse.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse2, HttpStatusCode.valueOf(200)));
        User user = userDetailsAPICall.execute(jwtToken);
        Assertions.assertEquals(user2, user);
    }

    @Test
    void whenNullExecuteTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userDetailsAPICall.execute(null);
        });
    }
}
