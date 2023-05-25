package id.ac.ui.cs.advprog.gamesappsstore.core.cart.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class IsAppInCartAPICallTest {
    private final String jwtToken = "Bearer aisojasoifjas.dsfsdjifsdofj";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private IsAppInCartAPICall isAppInCartAPICall;

    private AppData app;

    @BeforeEach
    void setup() {
        app = AppData.builder()
                .id(1L)
                .name("App 1")
                .userId(2)
                .imageUrl("https://image.com/app1")
                .installerUrl("https://storage.com/app1")
                .description("The first app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .verificationAdminId(null)
                .verificationDate(null)
                .build();
    }

    @Test
    void whenContainsTest() {
        String expectedResponse = """
                {
                    "id": 2,
                    "username": "eheez",
                    "cartDetailsData": [
                        {
                            "id": 1,
                            "appId": 1,
                            "appName": "Fate/Grand Order",
                            "appPrice": 2.0,
                            "addDate": "2022-10-10"
                        },
                        {
                            "id": 2,
                            "appId": 2,
                            "appName": "Fate/Grand Order 2",
                            "appPrice": 4.0,
                            "addDate": "2021-10-10"
                        }
                    ]
                }
                """;
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        boolean inCart = isAppInCartAPICall.execute(app, jwtToken);
        Assertions.assertTrue(inCart);
    }

    @Test
    void whenNotContainsTest() {
        String expectedResponse = """
                {
                    "id": 2,
                    "username": "eheez",
                    "cartDetailsData": [
                        {
                            "id": 1,
                            "appId": 4,
                            "appName": "Fate/Grand Order",
                            "appPrice": 2.0,
                            "addDate": "2022-10-10"
                        },
                        {
                            "id": 2,
                            "appId": 2,
                            "appName": "Fate/Grand Order 2",
                            "appPrice": 4.0,
                            "addDate": "2021-10-10"
                        }
                    ]
                }
                """;
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        boolean inCart = isAppInCartAPICall.execute(app, jwtToken);
        Assertions.assertFalse(inCart);
    }

    @Test
    void whenNullExecuteTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            isAppInCartAPICall.execute(null, jwtToken);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            isAppInCartAPICall.execute(app, null);
        });
    }
}
