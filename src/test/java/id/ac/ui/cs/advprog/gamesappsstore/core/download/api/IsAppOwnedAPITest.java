package id.ac.ui.cs.advprog.gamesappsstore.core.download.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.IsAppInCartAPICall;
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
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class IsAppOwnedAPITest {
    private final String jwtToken = "Bearer aisojasoifjas.dsfsdjifsdofj";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    @Autowired
    private IsAppOwnedAPICall isAppOwnedAPICall;

    private final Long id = 1L;
    private final String username = "bambang";

    @Test
    void whenOwnedTest() {
        String expectedResponse = "true";

        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        var isOwned = isAppOwnedAPICall.execute(username,id,jwtToken);
        Assertions.assertTrue(isOwned);
    }

    @Test
    void whenNotOwnedTest() {
        String expectedResponse = "false";

        Mockito.when(restTemplate.exchange(
                any(String.class),
                eq(HttpMethod.POST),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        var isOwned = isAppOwnedAPICall.execute(username,id,jwtToken);
        Assertions.assertFalse(isOwned);
    }

    @Test
    void whenNullExecuteTest() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            isAppOwnedAPICall.execute(null, id, jwtToken);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            isAppOwnedAPICall.execute(username, null, jwtToken);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            isAppOwnedAPICall.execute(username, id, null);
        });
    }
}
