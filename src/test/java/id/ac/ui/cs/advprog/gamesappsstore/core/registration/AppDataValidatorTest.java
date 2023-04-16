package id.ac.ui.cs.advprog.gamesappsstore.core.registration;

import id.ac.ui.cs.advprog.gamesappsstore.core.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.app.validator.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.dto.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.repository.appregistration.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.registration.AppRegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class AppDataValidatorTest {
    private AppDataValidator appDataValidator = new AppDataValidator();
    @InjectMocks
    private AppRegistrationServiceImpl appRegistrationService;
    @Mock
    private AppDataRepository appDataRepository;
    @Mock
    private Storage storage;

    AppData createMockApp() throws IOException {
        byte[] imageData = "dummy image data".getBytes();
        MultipartFile imageFile = new MockMultipartFile("image.png", imageData);

        byte[] installerData = "dummy installer data".getBytes();
        MultipartFile installerFile = new MockMultipartFile("installer.exe", installerData);

        String appName = "MyApp";
        String description = "This is a great app for all your needs.";
        String version = "1.0.0";
        Double price = 4.99;
        AppDataRequest appDataRequest = new AppDataRequest(appName, imageFile, description, installerFile, version, price);
        AppData appData = appRegistrationService.create(appDataRequest);
        return appData;
    }
    @Test
    void testValidationWithValidData() throws IOException {
        AppData appData = createMockApp();
        Assertions.assertTrue(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithInvalidVersionFormat() throws IOException {
        AppData appData = createMockApp();
        appData.setVersion("1/1/1");
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithNegativePrice() throws IOException {
        AppData appData = createMockApp();
        appData.setPrice(-4.99);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithTooLongDescription() throws IOException {
        AppData appData = createMockApp();
        String longDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Nullam iaculis orci non libero bibendum consectetur. Vivamus malesuada dui quis " +
                "lorem congue suscipit. Donec sit amet metus nulla. Morbi nec velit vel elit " +
                "iaculis luctus. Suspendisse potenti. In lobortis commodo augue, vel varius ex " +
                "convallis vitae. Etiam vestibulum diam a luctus varius. Etiam tristique, nunc " +
                "in ultricies vulputate, ante erat consequat lorem, non euismod velit velit vitae " +
                "neque. Quisque vel velit eget mauris interdum pharetra. Donec quis commodo nunc. " +
                "Praesent at libero eget odio commodo vestibulum. In vitae magna commodo, mollis " +
                "nisi nec, posuere libero. Donec lacinia nisl sit amet mi luctus, ut varius arcu " +
                "sollicitudin. ";
        appData.setDescription(longDescription);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithNullInstaller() throws IOException {
        AppData appData = createMockApp();
        appData.setInstallerUrl(null);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }
    @Test
    void testValidationWithNullAppName() throws IOException {
        AppData appData = createMockApp();
        appData.setName(null);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

}