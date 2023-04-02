package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.constants.DropboxKeys;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.UploadFileAPICall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class Storage {
    @Autowired
    DropboxKeys dropboxKeys;

    public String uploadFile(InputStream file, String path) {
        AccessTokenAPICall accessTokenAPICall = new AccessTokenAPICall(dropboxKeys);
        String accessToken = accessTokenAPICall.execute();

        UploadFileAPICall uploadFileAPICall = new UploadFileAPICall(accessToken, path, file);
        return uploadFileAPICall.execute();
    }
}
