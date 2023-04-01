package id.ac.ui.cs.advprog.gamesappsstore.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest
class StorageTest {
    @Value("${atmos.storage.token}")
    private String refreshToken;

    @Value("${atmos.storage.key}")
    private String appKey;

    @Value("${atmos.storage.secret}")
    private String appSecret;

    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage(refreshToken, appKey, appSecret);
    }

    @Test
    void uploadFile() {
        InputStream in = new ByteArrayInputStream("Ehe Te Nandayo".getBytes());
        storage.uploadFile(in, "/Test/venti.txt");
    }
}
