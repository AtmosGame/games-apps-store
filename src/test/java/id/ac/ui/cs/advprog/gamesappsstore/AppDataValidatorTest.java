package id.ac.ui.cs.advprog.gamesappsstore;

import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.AppRegistration.AppDataValidator;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationService;
import id.ac.ui.cs.advprog.gamesappsstore.service.AppRegistration.AppRegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

@Component
public class AppDataValidatorTest {
    AppDataValidator appDataValidator = new AppDataValidator();
    AppRegistrationService appRegistrationService = new AppRegistrationServiceImpl();

    @Test
    public void testValidationWithValidData(){
        AppData appData = new AppData("My App", "This is my app", "app.png", "app.zip", "1.0.0", 10.0);
        Assertions.assertTrue(appDataValidator.validate(appData));
    }

    @Test
    public void testValidationWithInvalidVersionFormat() {
        AppData appData = new AppData("My App", "This is my app", "app.png", "app.zip", "1.0", 10.0);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    public void testValidationWithNegativePrice() {
        AppData appData = new AppData("My App", "This is my app", "app.png", "app.zip", "1.0.0", -10.0);
        Assertions.assertFalse(appDataValidator.validate(appData));
    }

    @Test
    public void testValidationWithTooLongDescription() {

        AppData appData = new AppData("My App", "abc", "app.png", "app.zip", "1.0.0", -10.0);
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
    public void testIDGenerator(){
        AppData appData = new AppData("My App", "This is my app", "app.png", "app.zip", "1.0.0", 10.0);
        appRegistrationService.validateApp(appData);

    }

}