package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.download.AppDownloadResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AppDownloadServiceTest {
    @Autowired
    AppDataRepository appDataRepository;

    @Autowired
    AppDownloadServiceImpl appDownloadService;


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
                .verificationStatus(VerificationStatus.UNVERIFIED)
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
                .verificationStatus(VerificationStatus.VERIFIED)
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
                .price(0d)
                .verificationStatus(VerificationStatus.REJECTED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();

        appDataRepository.save(appData1);
        appDataRepository.save(appData2);
        appDataRepository.save(appData3);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDownloadUrlWithFreeApp(){
        String username = "testUser";
        String jwtToken = "testToken";
        Long appId = 3L;

        AppDownloadResponse response = appDownloadService.getDownloadUrl(username, appId, jwtToken);

        Assertions.assertEquals("https://storage.com/app3", response.getInstallerUrl());
    }
}