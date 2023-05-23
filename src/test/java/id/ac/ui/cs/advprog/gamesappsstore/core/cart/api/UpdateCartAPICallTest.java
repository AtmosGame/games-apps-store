package id.ac.ui.cs.advprog.gamesappsstore.core.cart.api;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UpdateCartAPICallTest {
    private final String jwtToken = "Bearer aisojasoifjas.dsfsdjifsdofj";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private UpdateCartAPICall updateCartAPICall;

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
    void whenSuccessful() {
        String expectedResponse = """
                Success
                """;
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));

        Assertions.assertDoesNotThrow(() -> {
            updateCartAPICall.execute(app, "ehee", jwtToken);
        });
    }

    @Test
    void whenUnsuccessful() {
        String expectedResponse = """
                Unsuccessful
                """;
        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(400)));

        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            updateCartAPICall.execute(app, "ehee", jwtToken);
        });
    }

    @Test
    void whenNullExecuteTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            updateCartAPICall.execute(null, "ehee", jwtToken);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            updateCartAPICall.execute(app, null, jwtToken);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            updateCartAPICall.execute(app, "ehee", null);
        });
    }
}
