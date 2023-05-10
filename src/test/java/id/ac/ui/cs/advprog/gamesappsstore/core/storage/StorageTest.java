package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.StorageAPICallCreator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Mock
    private StorageAPICallCreator storageAPICallCreator;

    @InjectMocks
    @Autowired
    private Storage storage;

    @Test
    void uploadFileTest() {
        String accessToken = "ini.access.tokennya";
        String path = "/Test/Hehe.txt";
        String expectedUrl = "https://storage.com/test/hehe.txt";

        InputStream file = new ByteArrayInputStream("Test Upload File".getBytes());

        Mockito.when(storageAPICallCreator.create("access_token")).thenReturn(accessTokenAPICall);
        Mockito.when(storageAPICallCreator.create("upload_file")).thenReturn(uploadFileAPICall);
        Mockito.when(storageAPICallCreator.create("share_url")).thenReturn(shareURLAPICall);
        Mockito.when(accessTokenAPICall.execute()).thenReturn(accessToken);
        Mockito.when(uploadFileAPICall.execute()).thenReturn(path);
        Mockito.when(shareURLAPICall.execute()).thenReturn(expectedUrl);
        String url = storage.uploadFile(file, path);
        Assertions.assertEquals(expectedUrl, url);
    }
}
