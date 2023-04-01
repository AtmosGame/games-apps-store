package id.ac.ui.cs.advprog.gamesappsstore.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DropboxKeys {
    @Value("${atmos.storage.token}")
    public String refreshToken;
    @Value("${atmos.storage.key}")
    public String appKey;
    @Value("${atmos.storage.secret}")
    public String appSecret;
}
