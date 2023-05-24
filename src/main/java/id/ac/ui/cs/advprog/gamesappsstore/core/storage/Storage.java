package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.StorageAPICallCreator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@NoArgsConstructor
public class Storage {
    @Value("${atmos.storage.token}")
    private String refreshToken;
    @Value("${atmos.storage.key}")
    private String appKey;
    @Value("${atmos.storage.secret}")
    private String appSecret;

    @Autowired
    private StorageAPICallCreator storageAPICallCreator ;

    public String uploadFile(InputStream file, String path) {
        AccessTokenAPICall accessTokenAPICall = (AccessTokenAPICall) storageAPICallCreator.create("access_token");
        accessTokenAPICall.setup(refreshToken, appKey, appSecret);
        String accessToken = accessTokenAPICall.execute();

        UploadFileAPICall uploadFileAPICall = (UploadFileAPICall) storageAPICallCreator.create("upload_file");
        uploadFileAPICall.setup(accessToken, path, file);
        String newPath = uploadFileAPICall.execute();

        ShareURLAPICall shareURLAPICall = (ShareURLAPICall) storageAPICallCreator.create("share_url");
        shareURLAPICall.setup(accessToken, newPath);
        String downloadUrl = shareURLAPICall.execute();

        return convertUrlToRaw(downloadUrl) + "?raw=1";
    }

    private String convertUrlToRaw(String url) {
        return url.split("\\?")[0];
    }
}
