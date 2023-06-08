package id.ac.ui.cs.advprog.gamesappsstore.service.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AppDetailServiceTest {
    @Autowired
    AppDataRepository appDataRepository;

    @Autowired
    AppDetailServiceImpl appDetailService;

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
    void findAll(){
        List<AppData> searchResults = appDataRepository.findAll();
        Assertions.assertEquals(3, searchResults.size());
    }
    @Test
    void findByAvailableApps(){
        AppDetailFullResponse response = appDetailService.getAppDetailbyId(1L);
        Assertions.assertEquals("Dota 1", response.getName());
    }

    @Test
    void findByUnavailableApps(){
        AppDetailFullResponse response = appDetailService.getAppDetailbyId(5L);
        Assertions.assertNull(response);
    }

    @Test
    void validateAppSuccess() {
        AppDetailValidationRequest request = AppDetailValidationRequest.builder()
                .id(1L)
                .name("Dota 1")
                .price(10000d)
                .build();

        AppData appData = AppData.builder()
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

        // Act
        AppDetailValidationResponse response = appDetailService.validateApp(request);

        // Assert
        Assertions.assertTrue(response.getIsValid());
    }

    @Test
    void validateAppDiffName() {
        AppDetailValidationRequest request = AppDetailValidationRequest.builder()
                .id(1L)
                .name("Dota 2")
                .price(10000d)
                .build();

        AppData appData = AppData.builder()
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

        // Act
        AppDetailValidationResponse response = appDetailService.validateApp(request);

        // Assert
        Assertions.assertFalse(response.getIsValid());
    }

    @Test
    void validateAppDiffPrice() {

        AppDetailValidationRequest request = AppDetailValidationRequest.builder()
                .id(1L)
                .name("Dota 1")
                .price(100002d)
                .build();

        AppData appData = AppData.builder()
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

        // Act
        AppDetailValidationResponse response = appDetailService.validateApp(request);

        // Assert
        Assertions.assertFalse(response.getIsValid());
    }

    @Test
    void validateIdNotFound() {
        AppDetailValidationRequest request = AppDetailValidationRequest.builder()
                .id(100L)
                .name("Dota 1")
                .price(100002d)
                .build();
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appDetailService.validateApp(request);
        });
    }
}