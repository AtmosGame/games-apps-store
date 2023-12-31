package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserDetailsAPICallTest {
    private final String jwtToken = "Bearer aisojasoifjas.dsfsdjifsdofj";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private UserDetailsAPICall userDetailsAPICall;

    @Test
    void whenUserTest() {
        String expectedResponse = """
                {
                  "id": 3,
                  "username": "eheez",
                  "role": "USER",
                  "profilePicture": "https://google.com",
                  "bio": "bio",
                  "applications": "applications",
                  "active": true
                }
                """;
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
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        User user = userDetailsAPICall.execute(jwtToken);
        Assertions.assertEquals(user3, user);
    }

    @Test
    void whenDeveloperTest() {
        String expectedResponse = """
                {
                  "id": 1,
                  "username": "ehee",
                  "role": "DEVELOPER",
                  "profilePicture": "https://google.com",
                  "bio": "bio",
                  "applications": "applications",
                  "active": true
                }
                """;
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
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        User user = userDetailsAPICall.execute(jwtToken);
        Assertions.assertEquals(user1, user);
    }

    @Test
    void whenAdminTest() {
        String expectedResponse = """
                {
                  "id": 2,
                  "username": "ehees",
                  "role": "ADMIN",
                  "profilePicture": "https://google.com",
                  "bio": "bio",
                  "applications": "applications",
                  "active": true
                }
                """;
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
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
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
