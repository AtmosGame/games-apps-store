package id.ac.ui.cs.advprog.gamesappsstore.controller.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.StatusResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.verification.VerificationDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.service.verification.VerificationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VerificationControllerTest {
    @Mock
    private VerificationService verificationService;

    @InjectMocks
    private VerificationController verificationController;

    AppData appData1;
    AppData appData2;
    AppData appData3;

    Date date2;

    @BeforeEach
    void setup() {
        date2 = new Date();

        appData1 = AppData.builder()
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
        appData2 = AppData.builder()
                .id(2L)
                .name("App 2")
                .imageUrl("https://image.com/app2")
                .installerUrl("https://storage.com/app2")
                .description("The second app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.VERIFIED)
                .verificationAdminId(1)
                .verificationDate(date2)
                .build();
        appData3 = AppData.builder()
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
    }

    @Test
    void getAllUnverifiedAppsTest() {
        List<AppData> expectedList = new ArrayList<>();
        expectedList.add(appData1);
        expectedList.add(appData2);
        expectedList.add(appData3);

        Mockito
                .when(verificationService.findAllUnverifiedApps())
                .thenReturn(expectedList);
        ResponseEntity<List<AppData>> response = verificationController.getAllUnverifiedApps();
        List<AppData> appList = response.getBody();
        Assertions.assertEquals(expectedList, appList);
    }

    @Test
    void getAppDetailTest() {
        VerificationDetailResponse expected = new VerificationDetailResponse(
                2L,
                "App 2",
                "https://image.com/app2",
                "The second app",
                "https://storage.com/app2",
                "1.0.0",
                10000d,
                "VERIFIED",
                1,
                date2
        );

        Mockito
                .when(verificationService.getAppDetail(2L))
                .thenReturn(expected);
        var response = verificationController.getAppDetail(2L);
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void verifyAppTest() {
        Mockito
                .doNothing()
                .when(verificationService)
                .verify(isA(Integer.class), isA(Long.class));
        ResponseEntity<StatusResponse> response = verificationController.verifyApp(1L);
        StatusResponse statusResponse = response.getBody();
        Assertions.assertNotNull(statusResponse);
        Assertions.assertEquals("Verified app with id 1", statusResponse.getMessage());
    }

    @Test
    void rejectAppTest() {
        Mockito
                .doNothing()
                .when(verificationService)
                .reject(isA(Integer.class), isA(Long.class));
        ResponseEntity<StatusResponse> response = verificationController.rejectApp(1L);
        StatusResponse statusResponse = response.getBody();
        assert statusResponse != null;
        Assertions.assertEquals("Rejected app with id 1", statusResponse.getMessage());
    }

    @Test
    void requestReverificationTest() {
        Mockito
                .doNothing()
                .when(verificationService)
                .requestReverification(isA(Long.class));
        ResponseEntity<StatusResponse> response = verificationController.requestReverificationApp(1L);
        StatusResponse statusResponse = response.getBody();
        assert statusResponse != null;
        Assertions.assertEquals("Requested reverification for app with id 1", statusResponse.getMessage());
    }
}