package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.StorageAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class StorageAPICallCreator {
    @Value("https://api.dropbox.com/oauth2/token")
    private String accessTokenEndPoint;

    @Value("https://content.dropboxapi.com/2/files/upload")
    private String uploadFileEndPoint;

    @Value("https://api.dropboxapi.com/2/sharing/create_shared_link_with_settings")
    private String shareURLEndPoint;

    public StorageAPICall create(String type) {
        switch (type) {
            case "access_token" -> {
                return new AccessTokenAPICall(accessTokenEndPoint);
            }
            case "upload_file" -> {
                return new UploadFileAPICall(uploadFileEndPoint);
            }
            case "share_url" -> {
                return new ShareURLAPICall(shareURLEndPoint);
            }
            default -> throw new IllegalArgumentException("Type is not valid");
        }
    }
}
