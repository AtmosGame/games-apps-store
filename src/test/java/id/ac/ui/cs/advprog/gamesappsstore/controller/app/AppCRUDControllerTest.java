package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.*;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.advice.GlobalExceptionHandler;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.ErrorTemplate;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppCRUDImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppCRUDControllerTest {
    @Mock
    AppCRUDImpl appCRUD;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;
    @InjectMocks
    AppCRUDController appCRUDController;
    AppData appData;
    AppDataRequest appDataRequest;
    AppProfileUpdate appProfileUpdate;
    AppImageUpdate appImageUpdate;
    AppInstallerUpdate appInstallerUpdate;
    User user;
    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(2)
                .username("ehee")
                .role(UserRole.DEVELOPER)
                .profilePicture("")
                .active(true)
                .build();
        SecurityContextHolder.setContext(securityContext);

        MultipartFile imagePng = new MockMultipartFile("image", "image.png",
                "image", "Spring Framework".getBytes());

        MultipartFile installerExe = new MockMultipartFile("installer", "installer.exe",
                "installer", "dummy installer data".getBytes());
        MultipartFile newImage = new MockMultipartFile("image", "pohon.png",
                "image", "Dota 2".getBytes());
        MultipartFile newInstaller = new MockMultipartFile("installer", "dota2.exe",
                "installer", "dota2thebest".getBytes());

        appDataRequest = AppDataRequest.builder()
                .appName("MyApp")
                .imageFile(imagePng)
                .description("This is a great app for all your needs")
                .installerFile(installerExe)
                .version("1.0.0")
                .price(4.99)
                .build();

        appProfileUpdate = appProfileUpdate.builder()
                .appName("negus")
                .description("king of ethiopian")
                .price(10.0)
                .build();

        appImageUpdate = appImageUpdate.builder()
                .imageFile(newImage)
                .build();

        appInstallerUpdate = appInstallerUpdate.builder()
                .installerFile(newInstaller)
                .version("1.0.1")
                .build();

        appData = AppData.builder()
                .id((long)1)
                .userId(1)
                .name("MyApp")
                .imageUrl("image.com")
                .description("This is a great app for all your needs")
                .installerUrl("installer.com")
                .version("1.0.0")
                .price(4.99)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .build();

    }

    @Test
    void handleTypeMismatchTest() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        AppDataDoesNotExistException exception = new AppDataDoesNotExistException();

        ResponseEntity<Object> responseEntity = exceptionHandler.errorException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedMessage = "Tidak App dengan id tersebut";
        ErrorTemplate errorTemplate = (ErrorTemplate) responseEntity.getBody();
        Assertions.assertEquals(expectedMessage, errorTemplate.message());

    }

    @Test
    void getAllAppTest(){
        List<AppDetailResponseStatus> appDetailResponseStatusList = new ArrayList<>();
        appDetailResponseStatusList.add(AppDetailResponseStatus.builder()
                .id(appData.getId())
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .verificationStatus(appData.getVerificationStatus())
                .build());

        Mockito.when(appCRUD.findAllApp()).thenReturn(appDetailResponseStatusList);

        var response = appCRUDController.getAll();
        Assertions.assertEquals(appDetailResponseStatusList, response.getBody());
    }
    @Test
    void postSubmitTest() throws IOException {
        AppData expected = appData;

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        when(appCRUD.create(any(Integer.class), any(AppDataRequest.class))).thenReturn(appData);

        var response = appCRUDController.submitForm(appDataRequest);
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void updateProfileTest() throws IOException {
        AppData expected = appData;
        appData.setName(appProfileUpdate.getAppName());
        appData.setDescription(appProfileUpdate.getDescription());
        appData.setPrice(appProfileUpdate.getPrice());

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        when(appCRUD.updateProfile(any(Long.class),any(AppProfileUpdate.class), any(Integer.class))).thenReturn(appData);

        var response = appCRUDController.updateProfile(1L, appProfileUpdate);
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void getAppTest() throws IOException {

        when(appCRUD.findById(any(Long.class))).thenReturn(appData);

        AppDetailResponseStatus expected = AppDetailResponseStatus.builder()
                .id(appData.getId())
                .name(appData.getName())
                .imageUrl(appData.getImageUrl())
                .description(appData.getDescription())
                .version(appData.getVersion())
                .price(appData.getPrice())
                .verificationStatus(appData.getVerificationStatus())
                .build();

        var response = appCRUDController.getApp(1L);
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void updateInstallerTest() throws IOException {
        AppData expected = appData;

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        when(appCRUD.updateInstaller(any(Long.class),any(AppInstallerUpdate.class), any(Integer.class))).thenReturn(appData);

        var response = appCRUDController.updateInstaller(1L, appInstallerUpdate.getInstallerFile(), appInstallerUpdate.getVersion());
        Assertions.assertEquals(expected, response.getBody());
    }

    @Test
    void updateImageTest() throws IOException {
        AppData expected = appData;

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        when(appCRUD.updateImage(any(Long.class),any(AppImageUpdate.class), any(Integer.class))).thenReturn(appData);

        var response = appCRUDController.updateImage(1L, appImageUpdate.getImageFile());
        Assertions.assertEquals(expected, response.getBody());
    }
    @Test
    void deleteTest() throws IOException {
        String expected = "Menghapus App dengan id 1";

        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        var response = appCRUDController.deleteApp(1L);
        Assertions.assertEquals(expected, response.getBody());
    }
}
