package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
@Import(Properties.class)
class ShareURLAPICallTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ShareURLAPICall shareURLAPICall = new ShareURLAPICall("apahayo");

    @BeforeEach
    void setup() {
        String accessToken = "ini.access.tokennya";
        String path = "/Homework/math/Prime_Numbers.txt";
        shareURLAPICall.setup(accessToken, path);
    }

    @Test
    void whenSuccessfulTest() {
        String expectedResponse = """
                {
                    ".tag": "file",
                    "client_modified": "2015-05-12T15:50:38Z",
                    "id": "id:a4ayc_80_OEAAAAAAAAAXw",
                    "link_permissions": {
                        "allow_comments": true,
                        "allow_download": true,
                        "audience_options": [
                            {
                                "allowed": true,
                                "audience": {
                                    ".tag": "public"
                                }
                            },
                            {
                                "allowed": false,
                                "audience": {
                                    ".tag": "team"
                                }
                            },
                            {
                                "allowed": true,
                                "audience": {
                                    ".tag": "no_one"
                                }
                            }
                        ],
                        "can_allow_download": true,
                        "can_disallow_download": false,
                        "can_remove_expiry": false,
                        "can_remove_password": true,
                        "can_revoke": false,
                        "can_set_expiry": false,
                        "can_set_password": true,
                        "can_use_extended_sharing_controls": false,
                        "require_password": false,
                        "resolved_visibility": {
                            ".tag": "public"
                        },
                        "revoke_failure_reason": {
                            ".tag": "owner_only"
                        },
                        "team_restricts_comments": true,
                        "visibility_policies": [
                            {
                                "allowed": true,
                                "policy": {
                                    ".tag": "public"
                                },
                                "resolved_policy": {
                                    ".tag": "public"
                                }
                            },
                            {
                                "allowed": true,
                                "policy": {
                                    ".tag": "password"
                                },
                                "resolved_policy": {
                                    ".tag": "password"
                                }
                            }
                        ]
                    },
                    "name": "Prime_Numbers.txt",
                    "path_lower": "/homework/math/prime_numbers.txt",
                    "rev": "a1c10ce0dd78",
                    "server_modified": "2015-05-12T15:50:38Z",
                    "size": 7212,
                    "team_member_info": {
                        "display_name": "Roger Rabbit",
                        "member_id": "dbmid:abcd1234",
                        "team_info": {
                            "id": "dbtid:AAFdgehTzw7WlXhZJsbGCLePe8RvQGYDr-I",
                            "name": "Acme, Inc."
                        }
                    },
                    "url": "https://www.dropbox.com/s/2sn712vy1ovegw8/Prime_Numbers.txt?dl=0"
                }
                """;
        String expectedShareURL = "https://www.dropbox.com/s/2sn712vy1ovegw8/Prime_Numbers.txt?dl=0";

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        String shareURL = shareURLAPICall.execute();
        Assertions.assertEquals(expectedShareURL, shareURL);
    }

    @Test
    void whenResponseInvalidTest() {
        String expectedResponse = "{";

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            shareURLAPICall.execute();
        });
    }

    @Test
    void whenErrorOnUploadingTest() {
        String expectedResponse = """
                {
                     "error": {
                         ".tag": "other"
                     },
                     "error_summary": "other/..."
                }
                """;

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            shareURLAPICall.execute();
        });
    }

    @Test
    void noSetupTest() {
        ShareURLAPICall shareURLAPICall1 = new ShareURLAPICall("apahayo");
        Assertions.assertThrows(NoSetupException.class, shareURLAPICall1::execute);
    }

    @Test
    void nullArgumentSetupTest() {
        ShareURLAPICall shareURLAPICall1 = new ShareURLAPICall("apahayo");
        Assertions.assertThrows(NullPointerException.class, () -> {
            shareURLAPICall1.setup(null, "a");
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            shareURLAPICall1.setup("a", null);
        });
    }
}
