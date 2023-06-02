package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.SearchServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class SearchServiceTest {
    @Autowired
    AppDataRepository appDataRepository;

    @Autowired
    SearchServiceImpl searchService;

    @BeforeEach
    void setup() {
        AppData appData1 = AppData.builder()
                .id(1L)
                .name("Dota 1")
                .imageUrl("https://image.com/app1")
                .installerUrl("https://storage.com/app1")
                .description("The first app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.VERIFIED)
                .verificationAdminId(null)
                .verificationDate(null)
                .build();
        AppData appData2 = AppData.builder()
                .id(2L)
                .name("dota 2")
                .imageUrl("https://image.com/app2")
                .installerUrl("https://storage.com/app2")
                .description("The second app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();
        AppData appData3 = AppData.builder()
                .id(3L)
                .name("Mobile Legends")
                .imageUrl("https://image.com/app3")
                .installerUrl("https://storage.com/app3")
                .description("The third app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.VERIFIED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();

        appDataRepository.save(appData1);
        appDataRepository.save(appData2);
        appDataRepository.save(appData3);
    }
    @Test
    void findAll(){
        List<AppData> searchResults = appDataRepository.findAll();
        Assertions.assertEquals(3, searchResults.size());
    }
    @Test
    void searchWithFullMatchingKeyword() {
        List<AppData> searchResults = searchService.searchAppsByKeyword("Mobile Legends");
        Assertions.assertEquals(1, searchResults.size());
        AppData appData = searchResults.get(0);
        Assertions.assertEquals("Mobile Legends", appData.getName());
    }

    @Test
    void searchWithNonMatchingKeyword() {
        List<AppData> searchResults = searchService.searchAppsByKeyword("nonexistent");
        Assertions.assertEquals(0, searchResults.size());
    }

    @Test
    void searchWithPartialMatchingKeyword() {
        List<AppData> searchResults = searchService.searchAppsByKeyword("DOTA");
        Assertions.assertEquals(1, searchResults.size());
    }

    @Test
    void searchWithNullKeyword() {
        List<AppData> searchResults = searchService.searchAppsByKeyword(null);
        Assertions.assertEquals(0, searchResults.size());
    }

    @Test
    void searchWithEmptyKeyword() {
        List<AppData> searchResults = searchService.searchAppsByKeyword("");
        Assertions.assertEquals(2, searchResults.size());
    }


}