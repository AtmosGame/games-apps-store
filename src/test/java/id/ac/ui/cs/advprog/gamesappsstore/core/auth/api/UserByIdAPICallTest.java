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
class UserByIdAPICallTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private UserByIdAPICall userByIdAPICall;

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
        User expectedUser = User.builder()
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
        User user = userByIdAPICall.execute(3);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void whenAdminTest() {
        String expectedResponse = """
                {
                  "id": 3,
                  "username": "eheez",
                  "role": "ADMIN",
                  "profilePicture": "https://google.com",
                  "bio": "bio",
                  "applications": "applications",
                  "active": true
                }
                """;
        User expectedUser = User.builder()
                .id(3)
                .username("eheez")
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
        User user = userByIdAPICall.execute(3);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void whenDeveloperTest() {
        String expectedResponse = """
                {
                  "id": 3,
                  "username": "eheez",
                  "role": "DEVELOPER",
                  "profilePicture": "https://google.com",
                  "bio": "bio",
                  "applications": "applications",
                  "active": true
                }
                """;
        User expectedUser = User.builder()
                .id(3)
                .username("eheez")
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
        User user = userByIdAPICall.execute(3);
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void whenNullExecuteTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            userByIdAPICall.execute(null);
        });
    }
}
