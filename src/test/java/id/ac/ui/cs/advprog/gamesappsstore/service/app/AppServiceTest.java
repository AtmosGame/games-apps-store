package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.AppDataNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AppServiceTest {
    @Autowired
    private AppDataRepository appDataRepository;

    @Autowired
    private AppService appService;

    @BeforeEach
    void setup() {
        AppData appData1 = AppData.builder()
                .id(1L)
                .name("App 1")
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
                .name("App 2")
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
                .name("App 3")
                .imageUrl("https://image.com/app3")
                .installerUrl("https://storage.com/app3")
                .description("The third app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.REJECTED)
                .verificationAdminId(1)
                .verificationDate(new Date())
                .build();

        appDataRepository.save(appData1);
        appDataRepository.save(appData2);
        appDataRepository.save(appData3);
    }

    @Test
    void getAppDetailTest() {
        AppDetailResponse expected = new AppDetailResponse(
                2L,
                "App 2",
                "https://image.com/app2",
                "The second app",
                "1.0.0",
                10000d
        );

        AppDetailResponse response = appService.getAppDetail(2L);

        Assertions.assertEquals(expected, response);
    }

    @Test
    void appNotFound() {
        Assertions.assertThrows(AppDataNotFoundException.class, () -> {
            appService.getAppDetail(4L);
        });
    }
}
