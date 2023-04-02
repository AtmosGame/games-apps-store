package id.ac.ui.cs.advprog.gamesappsstore.core.storage;

import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@SpringBootTest
class StorageTest {
    @Autowired
    private Storage storage;

    @Test
    void uploadFile() {
        InputStream in = new ByteArrayInputStream("Ehe Te Nandayo".getBytes());
        storage.uploadFile(in, "/Test/ventix.txt");
    }
}