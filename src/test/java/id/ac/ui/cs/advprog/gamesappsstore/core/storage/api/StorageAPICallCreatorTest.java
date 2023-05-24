package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StorageAPICallCreatorTest {
    @Autowired
    private StorageAPICallCreator storageAPICallCreator;

    @Test
    void createAccessTokenAPICallTest() {
        var apiCall = storageAPICallCreator.create("access_token");
        Assertions.assertTrue(apiCall instanceof AccessTokenAPICall);
    }

    @Test
    void createUploadFileAPICallTest() {
        var apiCall = storageAPICallCreator.create("upload_file");
        Assertions.assertTrue(apiCall instanceof UploadFileAPICall);
    }

    @Test
    void createShareURLAPICallTest() {
        var apiCall = storageAPICallCreator.create("share_url");
        Assertions.assertTrue(apiCall instanceof ShareURLAPICall);
    }

    @Test
    void illegalCreateTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            storageAPICallCreator.create("ehee");
        });
    }
}
