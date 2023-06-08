package id.ac.ui.cs.advprog.gamesappsstore.service.verification;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.verification.VerificationDetailResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class VerificationServiceTest {
    @Autowired
    private AppDataRepository appDataRepository;

    @InjectMocks
    @Autowired
    private VerificationServiceImpl verificationService;

    private Date date2;

    @BeforeEach
    void setup() {
        date2 = new Date();

        AppData appData1 = AppData.builder()
                .id(1L)
                .name("App 1")
                .userId(2)
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
                .userId(2)
                .imageUrl("https://image.com/app2")
                .installerUrl("https://storage.com/app2")
                .description("The second app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.VERIFIED)
                .verificationAdminId(1)
                .verificationDate(date2)
                .build();
        AppData appData3 = AppData.builder()
                .id(3L)
                .name("App 3")
                .userId(2)
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
    void findAllUnverifiedAppsTest() {
        List<AppData> unverifiedApps = verificationService.findAllUnverifiedApps();
        Assertions.assertEquals(1, unverifiedApps.size());
    }

    @Test
    void findAllVerifiedAppsTest() {
        List<AppData> verifiedApps = verificationService.findAllVerifiedApps();
        Assertions.assertEquals(1, verifiedApps.size());
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

        VerificationDetailResponse response = verificationService.getAppDetail(2L);

        Assertions.assertEquals(expected, response);
    }

    @Test
    void appNotFound() {
        User user = User.builder()
                .id(1)
                .username("eheez")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            verificationService.verify(user, 4L);
        });
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            verificationService.reject(user, 4L);
        });
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            verificationService.requestReverification(user, 4L);
        });
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            verificationService.getAppDetail(4L);
        });
    }

    @Test
    void verifyApp() {
        User user = User.builder()
                .id(1)
                .username("eheez")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        verificationService.verify(user, 1L);

        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();
        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
        Assertions.assertEquals(1, appData.getVerificationAdminId());
    }

    @Test
    void rejectApp() {
        User user = User.builder()
                .id(1)
                .username("eheez")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        verificationService.reject(user, 1L);

        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();
        Assertions.assertEquals(VerificationStatus.REJECTED, appData.getVerificationStatus());
        Assertions.assertEquals(1, appData.getVerificationAdminId());
    }

    @Test
    void requestReverificationOwner() {
        User user = User.builder()
                .id(2)
                .username("eheez")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        verificationService.requestReverification(user, 3L);

        var appDataOptional = appDataRepository.findById(3L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();
        Assertions.assertEquals(VerificationStatus.UNVERIFIED, appData.getVerificationStatus());
        Assertions.assertNull(appData.getVerificationAdminId());
    }

    @Test
    void requestReverificationNotOwner() {
        User user = User.builder()
                .id(1)
                .username("eheez")
                .role(UserRole.ADMIN)
                .active(true)
                .build();

        Assertions.assertThrows(UnauthorizedException.class, () -> {
            verificationService.requestReverification(user, 3L);
        });
    }
}