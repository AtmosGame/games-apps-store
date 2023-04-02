package id.ac.ui.cs.advprog.gamesappsstore.AppRegistration;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationService;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
class AppDataValidatorTest {
    AppDataValidator appDataValidator = new AppDataValidator();
    AppRegistrationService appRegistrationService = new AppRegistrationServiceImpl();

    AppData createMockApp(){
        byte[] imageData = "dummy image data".getBytes();
        MultipartFile imageFile = new MockMultipartFile("image.png", imageData);

        byte[] installerData = "dummy installer data".getBytes();
        MultipartFile installerFile = new MockMultipartFile("installer.exe", installerData);

        String appName = "MyApp";
        String description = "This is a great app for all your needs.";
        String version = "1.0.0";
        Double price = 4.99;
        AppData appData = new AppData(appName, description, imageFile, installerFile, version, price);
        return appData;
    }
    @Test
    void testValidationWithValidData(){
        AppData appData = createMockApp();
        Assertions.assertTrue(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithInvalidVersionFormat() {
        AppData appData = createMockApp();
        appData.setVersion("1/1/1");
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithNegativePrice() {
        AppData appData = createMockApp();
        appData.setPrice(-4.99);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    void testValidationWithTooLongDescription() {
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
    void testValidationWithNullInstaller(){
        AppData appData = createMockApp();
        appData.setInstallerFile(null);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }
    @Test
    void testValidationWithNullAppName(){
        AppData appData = createMockApp();
        appData.setName(null);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

}