package id.ac.ui.cs.advprog.gamesappsstore.core.verification;

import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.user.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.user.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.AppDataVerificationState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.UnverifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.core.verification.states.VerifiedState;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ForbiddenMethodCall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
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
class AppDataVerificationTest {
    @Autowired
    private AppDataRepository appDataRepository;

    private User admin;
    private User customer;

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

        admin = new User(1, UserRole.ADMINISTRATOR);
        customer = new User(2, UserRole.CUSTOMER);
    }

    @Test
    void verifyUnverifiedByAdmin() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        verification.verify(admin);

        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
    }

    @Test
    void verifyUnverifiedByNonAdmin() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            verification.verify(customer);
        });

        Assertions.assertEquals(VerificationStatus.UNVERIFIED, appData.getVerificationStatus());
    }

    @Test
    void rejectUnverifiedByAdmin() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        verification.reject(admin);

        Assertions.assertEquals(VerificationStatus.REJECTED, appData.getVerificationStatus());
    }

    @Test
    void rejectUnverifiedByNonAdmin() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            verification.reject(customer);
        });

        Assertions.assertEquals(VerificationStatus.UNVERIFIED, appData.getVerificationStatus());
    }

    @Test
    void requestReverificationOnUnverified() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(ForbiddenMethodCall.class, verification::requestReverification);

        Assertions.assertEquals(VerificationStatus.UNVERIFIED, appData.getVerificationStatus());
    }

    @Test
    void verifyVerified() {
        var appDataOptional = appDataRepository.findById(2L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(ForbiddenMethodCall.class, () -> {
            verification.verify(admin);
        });

        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
    }
    @Test
    void rejectVerifiedByAdmin() {
        var appDataOptional = appDataRepository.findById(2L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        verification.reject(admin);

        Assertions.assertEquals(VerificationStatus.REJECTED, appData.getVerificationStatus());
    }

    @Test
    void rejectVerifiedByNonAdmin() {
        var appDataOptional = appDataRepository.findById(2L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            verification.reject(customer);
        });

        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
    }
    @Test
    void requestReverificationOnVerified() {
        var appDataOptional = appDataRepository.findById(2L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(ForbiddenMethodCall.class, verification::requestReverification);

        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
    }

    @Test
    void verifyRejectedByAdmin() {
        var appDataOptional = appDataRepository.findById(3L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        verification.verify(admin);

        Assertions.assertEquals(VerificationStatus.VERIFIED, appData.getVerificationStatus());
    }

    @Test
    void verifyRejectedByNonAdmin() {
        var appDataOptional = appDataRepository.findById(3L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(UnauthorizedException.class, () -> {
            verification.verify(customer);
        });

        Assertions.assertEquals(VerificationStatus.REJECTED, appData.getVerificationStatus());
    }

    @Test
    void rejectRejected() {
        var appDataOptional = appDataRepository.findById(3L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(ForbiddenMethodCall.class, () -> {
            verification.reject(admin);
        });

        Assertions.assertEquals(VerificationStatus.REJECTED, appData.getVerificationStatus());
    }

    @Test
    void requestReverificationOnRejected() {
        var appDataOptional = appDataRepository.findById(3L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerification verification = new AppDataVerification(appData);
        verification.requestReverification();

        Assertions.assertEquals(VerificationStatus.UNVERIFIED, appData.getVerificationStatus());
    }

    @Test
    void changeStateSingleParameterToNotUnverified() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerificationState newState = new VerifiedState();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            verification.changeState(newState);
        });
    }

    @Test
    void changeStateMultipleParameterToUnverified() {
        var appDataOptional = appDataRepository.findById(1L);
        Assertions.assertTrue(appDataOptional.isPresent());
        AppData appData = appDataOptional.get();

        AppDataVerificationState newState = new UnverifiedState();
        Date newDate = new Date();

        AppDataVerification verification = new AppDataVerification(appData);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            verification.changeState(newState, admin, newDate);
        });
    }
}
