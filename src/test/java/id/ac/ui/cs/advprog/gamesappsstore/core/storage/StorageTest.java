package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.UploadFileAPICall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class StorageTest {
    @Mock
    private AccessTokenAPICall accessTokenAPICall;
    @Mock
    private UploadFileAPICall uploadFileAPICall;
    @Mock
    private ShareURLAPICall shareURLAPICall;

    @InjectMocks
    private Storage storage = new Storage();

    @Test
    void uploadFileTest() {
        String accessToken = "ini.access.tokennya";
        String path = "/Test/Hehe.txt";
        String expectedUrl = "https://storage.com/test/hehe.txt";

        InputStream file = new ByteArrayInputStream("Test Upload File".getBytes());

        Mockito.when(accessTokenAPICall.execute()).thenReturn(accessToken);
        Mockito.when(uploadFileAPICall.execute()).thenReturn(path);
        Mockito.when(shareURLAPICall.execute()).thenReturn(expectedUrl);
        String url = storage.uploadFile(file, path);
        Assertions.assertEquals(expectedUrl, url);
    }
}
