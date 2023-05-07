package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.AccessTokenAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.ShareURLAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.StorageAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call.UploadFileAPICall;

public class StorageAPICallCreator {
    public StorageAPICall create(String type) {
        switch (type) {
            case "access_token" -> {
                return new AccessTokenAPICall();
            }
            case "upload_file" -> {
                return new UploadFileAPICall();
            }
            case "share_url" -> {
                return new ShareURLAPICall();
            }
            default -> throw new IllegalArgumentException("Type is not valid");
        }
    }
}
