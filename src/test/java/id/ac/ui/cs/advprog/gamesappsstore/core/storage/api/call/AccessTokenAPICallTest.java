package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class AccessTokenAPICallTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AccessTokenAPICall accessTokenAPICall = new AccessTokenAPICall("apahayo");

    @BeforeEach
    void setup() {
        String refreshToken = "EE";
        String appKey = "OO";
        String appSecret = "HEE";
        accessTokenAPICall.setup(refreshToken, appKey, appSecret);
    }

    @Test
    void whenTokensValidTest() {
        String expectedResponse = """
                {
                  "access_token": "ini.access.tokennya",
                  "expires_in": 14400,
                  "token_type": "bearer",
                  "scope": "account_info.read files.content.read files.content.write files.metadata.read",
                  "account_id": "dbid:AAH4f99T0taONIb-OurWxbNQ6ywGRopQngc",
                  "uid": "12345"
                }
                """;
        String expectedAccessToken = "ini.access.tokennya";

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        String accessToken = accessTokenAPICall.execute();
        Assertions.assertEquals(expectedAccessToken, accessToken);
    }

    @Test
    void whenTokensInvalidTest() {
        String expectedResponse = """
                {
                  "error_summary": "other/...",
                  "error": {
                    ".tag": "other"
                  }
                }
                """;

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            accessTokenAPICall.execute();
        });
    }

    @Test
    void whenResponseInvalid() {
        String expectedResponse = "{";

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            accessTokenAPICall.execute();
        });
    }

    @Test
    void noSetupTest() {
        AccessTokenAPICall accessTokenAPICall1 = new AccessTokenAPICall("apahayo");
        Assertions.assertThrows(NoSetupException.class, accessTokenAPICall1::execute);
    }
}
