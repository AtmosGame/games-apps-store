package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.UploadFileAPICall;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class Storage {
    @Value("${atmos.storage.token}")
    private String refreshToken;
    @Value("${atmos.storage.key}")
    private String appKey;
    @Value("${atmos.storage.secret}")
    private String appSecret;

    private AccessTokenAPICall accessTokenAPICall = new AccessTokenAPICall();
    private UploadFileAPICall uploadFileAPICall = new UploadFileAPICall();
    private ShareURLAPICall shareURLAPICall = new ShareURLAPICall();

    public String uploadFile(InputStream file, String path) {
        accessTokenAPICall.setup(refreshToken, appKey, appSecret);
        String accessToken = accessTokenAPICall.execute();

        uploadFileAPICall.setup(accessToken, path, file);
        String newPath = uploadFileAPICall.execute();

        shareURLAPICall.setup(accessToken, newPath);
        return shareURLAPICall.execute();
    }
}
