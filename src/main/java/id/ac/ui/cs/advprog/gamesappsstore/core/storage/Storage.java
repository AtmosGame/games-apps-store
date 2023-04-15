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

    public String uploadFile(InputStream file, String path) {
        AccessTokenAPICall accessTokenAPICall = new AccessTokenAPICall(refreshToken, appKey, appSecret);
        String accessToken = accessTokenAPICall.execute();

        UploadFileAPICall uploadFileAPICall = new UploadFileAPICall(accessToken, path, file);
        uploadFileAPICall.execute();

        ShareURLAPICall shareURLAPICall = new ShareURLAPICall(accessToken, path);
        return shareURLAPICall.execute();
    }

//    public void printKeys() {
//        System.out.println(refreshToken + " " + appKey + " " + appSecret);
//    }
}
