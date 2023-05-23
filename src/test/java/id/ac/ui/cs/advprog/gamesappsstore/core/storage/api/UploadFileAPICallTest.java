package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.PayloadTooLargeException;
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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class UploadFileAPICallTest {
    private final String path = "/Homework/math/Prime_Numbers.txt";
    private final InputStream file = new ByteArrayInputStream("File String".getBytes());

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UploadFileAPICall uploadFileAPICall = new UploadFileAPICall("apahayo");

    @BeforeEach
    void setup() {
        String accessToken = "ini.access.tokennya";
        uploadFileAPICall.setup(accessToken, path, file);
    }

    @Test
    void whenSuccessfulTest() {
        String expectedResponse = """
                {
                    "client_modified": "2015-05-12T15:50:38Z",
                    "content_hash": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855",
                    "file_lock_info": {
                        "created": "2015-05-12T15:50:38Z",
                        "is_lockholder": true,
                        "lockholder_name": "Imaginary User"
                    },
                    "has_explicit_shared_members": false,
                    "id": "id:a4ayc_80_OEAAAAAAAAAXw",
                    "is_downloadable": true,
                    "name": "Prime_Numbers.txt",
                    "path_display": "/Homework/math/Prime_Numbers.txt",
                    "path_lower": "/homework/math/prime_numbers.txt",
                    "property_groups": [
                        {
                            "fields": [
                                {
                                    "name": "Security Policy",
                                    "value": "Confidential"
                                }
                            ],
                            "template_id": "ptid:1a5n2i6d3OYEAAAAAAAAAYa"
                        }
                    ],
                    "rev": "a1c10ce0dd78",
                    "server_modified": "2015-05-12T15:50:38Z",
                    "sharing_info": {
                        "modified_by": "dbid:AAH4f99T0taONIb-OurWxbNQ6ywGRopQngc",
                        "parent_shared_folder_id": "84528192421",
                        "read_only": true
                    },
                    "size": 7212
                }
                """;

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        String pathReal = uploadFileAPICall.execute();
        Assertions.assertEquals(path, pathReal);
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
            uploadFileAPICall.execute();
        });
    }

    @Test
    void whenPayloadTooLargeTest() {
        String expectedResponse = """
                {
                    "error": {
                        ".tag": "payload_too_large"
                    },
                    "error_summary": "payload_too_large/..."
                }
                """;

        Mockito.when(restTemplate.postForEntity(
                any(String.class),
                any(HttpEntity.class),
                eq(String.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatusCode.valueOf(200)));
        Assertions.assertThrows(PayloadTooLargeException.class, () -> {
            uploadFileAPICall.execute();
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
            uploadFileAPICall.execute();
        });
    }

    @Test
    void noSetupTest() {
        UploadFileAPICall uploadFileAPICall1 = new UploadFileAPICall("apahayo");
        Assertions.assertThrows(NoSetupException.class, uploadFileAPICall1::execute);
    }
}
