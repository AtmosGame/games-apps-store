package id.ac.ui.cs.advprog.gamesappsstore.service.appCRUD;

import id.ac.ui.cs.advprog.gamesappsstore.core.notification.AppDev;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.core.storage.Storage;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.AppDataRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.AppImageUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.AppInstallerUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.dto.appcrud.AppProfileUpdate;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.*;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.models.notification.NotificationData;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import id.ac.ui.cs.advprog.gamesappsstore.repository.notification.AppDeveloperRepository;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppCRUDImpl;
import id.ac.ui.cs.advprog.gamesappsstore.service.notification.NotificationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppCRUDTest {
    @InjectMocks
    private AppCRUDImpl appCRUD;
    @Mock
    Storage storage;
    @Mock
    AppDataRepository appDataRepository;
    @Mock
    AppDeveloperRepository appDeveloperRepository;
    @Mock
    NotificationServiceImpl notificationService;

    AppDataRequest submitRequest;
    AppProfileUpdate appProfileUpdate;
    AppImageUpdate appImageUpdate;
    AppInstallerUpdate appInstallerUpdate;
    AppData appData;

    @BeforeEach
    void setUp() throws IOException {
        MultipartFile imagePng = new MockMultipartFile("image", "image.png",
                "image", "Spring Framework".getBytes());

        MultipartFile installerExe = new MockMultipartFile("installer", "installer.exe",
                "installer", "dummy installer data".getBytes());
        MultipartFile newImage = new MockMultipartFile("image", "pohon.png",
                "image", "Dota 2".getBytes());
        MultipartFile newInstaller = new MockMultipartFile("installer", "dota2.exe",
                "installer", "dota2thebest".getBytes());

        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });

        submitRequest = AppDataRequest.builder()
                .appName("MyApp")
                .imageFile(imagePng)
                .description("This is a great app for all your needs")
                .installerFile(installerExe)
                .version("1.0.0")
                .price(4.99)
                .build();

        appProfileUpdate = AppProfileUpdate.builder()
                .appName("stack.pas")
                .price(1000.0)
                .description("dota 2 mantep")
                .build();

        appImageUpdate = AppImageUpdate.builder().imageFile(newImage).build();
        appInstallerUpdate = AppInstallerUpdate.builder()
                .installerFile(newInstaller)
                .version("2.2.2")
                .build();

        appData = AppData.builder()
                .id((long)1)
                .userId(1)
                .name("MyApp")
                .imageUrl(appCRUD.storeFile(imagePng))
                .description("This is a great app for all your needs")
                .installerUrl(appCRUD.storeFile(installerExe))
                .version("1.0.0")
                .price(4.99)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .build();
    }
    @AfterEach
    void reset(){
        appDataRepository.deleteAll();
    }
    @Test
    void testValidationWithValidData() throws IOException {
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        AppData result = appCRUD.create(1, submitRequest);
        verify(storage, atLeastOnce()).uploadFile(any(InputStream.class), anyString());
        verify(appDataRepository, atLeastOnce()).save(any(AppData.class));
        Assertions.assertEquals(appData, result);
    }

    @Test
    void testValidationWithInvalidVersionFormat() {
        AppDataRequest invalidVersion = submitRequest;
        invalidVersion.setVersion("1/0/0");
        Assertions.assertThrows(InvalidVersionException.class, () ->{
            appCRUD.create(1, invalidVersion);
        });
    }
    @Test
    void testValidationWithNegativePrice() throws IOException {
        AppDataRequest invalidPrice = submitRequest;
        invalidPrice.setPrice(-4.999);
        Assertions.assertThrows(PriceRangeException.class, () ->{
            appCRUD.create(1,invalidPrice);
        });
    }

    @Test
    void testValidationWithTooLongDescription() throws IOException {
        AppDataRequest longDesc = submitRequest;
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
        longDesc.setDescription(longDescription);
        Assertions.assertThrows(LongDescriptionException.class, () ->{
            appCRUD.create(1, longDesc);
        });
    }

    @Test
    void testValidationWithNullDescription() throws IOException {
        AppDataRequest nullDesc = submitRequest;
        nullDesc.setDescription(null);
        Assertions.assertThrows(EmptyFormException.class, () ->{
            appCRUD.create(1, nullDesc);
        });
    }

    @Test
    void testValidationWithEmptyDescription() throws IOException {
        AppDataRequest emptyDesc = submitRequest;
        String emptyDescription = "";
        emptyDesc.setDescription(emptyDescription);
        Assertions.assertThrows(EmptyFormException.class, () ->{
            appCRUD.create(1, emptyDesc);
        });
    }

    @Test
    void findAllApp()throws IOException{
        List<AppData> dummyList = new ArrayList<>();
        dummyList.add(appData);
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findAll()).thenReturn(dummyList);

        appCRUD.create(1, submitRequest);
        List<AppData> searchResults = appCRUD.getAllAppData();
        Assertions.assertEquals(1, searchResults.size());
    }
    @Test
    void testValidationWithNullInstaller() throws IOException {
        AppDataRequest nullInstaller = submitRequest;
        nullInstaller.setInstallerFile(new MockMultipartFile(
                "emptyFile",  // filename
                new byte[0]   // content as an empty byte array
        ));
        Assertions.assertThrows(EmptyFormException.class, () ->{
            appCRUD.create(1, nullInstaller);
        });
    }
    @Test
    void testValidationWithNullAppName() throws IOException {
        AppDataRequest nullName = submitRequest;
        nullName.setAppName(null);
        Assertions.assertThrows(EmptyFormException.class, () -> {
            appCRUD.create(1, nullName);
        });
    }

    @Test
    void findByIdAndFound() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        AppData appData1 = appCRUD.create(1, submitRequest);
        AppData result = appCRUD.findById((long)1);
        Assertions.assertEquals(result, appData1);
    }
    @Test
    void findByIdAndNotFound() throws IOException{
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appCRUD.findById((long)1);
        });
    }

    @Test
    void updateProfile() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        appProfileUpdate.setAppName("negus");
        AppData appDataBfr = appCRUD.create(1, submitRequest);
        AppData result = appCRUD.updateProfile(appDataBfr.getId(), appProfileUpdate, 1);
        AppData appData1 = appDataBfr;

        appProfileUpdate.setAppName("negus");
        appData1.setName(appProfileUpdate.getAppName());
        appData1.setDescription(appProfileUpdate.getDescription());
        appData1.setPrice(appProfileUpdate.getPrice());
        Assertions.assertEquals(result, appData1);
    }

    @Test
    void updateProfileEmpty() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        AppData appDataBfr = appCRUD.create(1, submitRequest);
        appProfileUpdate.setAppName("");

        Assertions.assertThrows(EmptyFormException.class, () -> {
            appCRUD.updateProfile(1L, appProfileUpdate, 1);
        });
    }

    @Test
    void updateProfileDoesNotExist() throws IOException{
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appCRUD.updateProfile((long)1, appProfileUpdate, 1);
        });
    }

    @Test
    void updatePriceNull() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        appCRUD.create(1, submitRequest);

        appProfileUpdate.setPrice(null);
        Assertions.assertThrows(EmptyFormException.class, () -> {
            appCRUD.updateProfile((long)1, appProfileUpdate, 1);
        });
    }

    @Test
    void updatePriceReallyBig() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        appCRUD.create(1, submitRequest);

        appProfileUpdate.setPrice( 1_0000_0000_000.0);
        Assertions.assertThrows(PriceRangeException.class, () -> {
            appCRUD.updateProfile((long)1, appProfileUpdate, 1);
        });
    }

    @Test
    void updateImage() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        MultipartFile newImage = new MockMultipartFile("image", "image.png",
                "image", "gambar apa tu man".getBytes());
        submitRequest.setImageFile(newImage);
        AppData appDataBfr = appCRUD.create(1, submitRequest);
        AppData result = appCRUD.updateImage((long)appDataBfr.getId(), appImageUpdate, 1);
        AppData appData1 = appDataBfr;
        appData1.setImageUrl(result.getImageUrl());
        Assertions.assertEquals(result, appData1);
    }

    @Test
    void updateImageDoesNotExist() throws IOException{
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appCRUD.updateImage((long)1, appImageUpdate, 1);
        });
    }

    @Test
    void updateInstaller() throws IOException{
        NotificationData notificationData = NotificationData.builder()
                .id(1L)
                .subjectId(1L)
                .description("Terdapat Update pada Aplikasi ANDA")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .subscriber(new ArrayList<>())
                .build();

        AppDev appDev = AppDev.builder()
                .id(1L)
                .appId((long)1)
                .subscribers(new ArrayList<>())
                .build();

        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));
        when(appDeveloperRepository.findByAppId(any(Long.class))).thenReturn(Optional.of(appDev));
        when(notificationService.handleNewBroadcast(any(Long.class), any(String.class))).thenReturn(notificationData);

        AppData appDataBfr = appCRUD.create(1, submitRequest);
        AppData result = appCRUD.updateInstaller((long)appDataBfr.getId(), appInstallerUpdate, 1);
        AppData appData1 = appDataBfr;
        appData1.setVersion(appInstallerUpdate.getVersion());
        appData1.setInstallerUrl(result.getInstallerUrl());
        Assertions.assertEquals(result, appData1);
    }

    @Test
    void updateInstallerDoesNotExist() throws IOException{
        MultipartFile emptyFile = new MockMultipartFile(
                "emptyFile",  // filename
                new byte[0]);
        appInstallerUpdate.setInstallerFile(emptyFile);
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appCRUD.updateInstaller((long)100, appInstallerUpdate, 1);
        });
    }
<<<<<<< src/test/java/id/ac/ui/cs/advprog/gamesappsstore/service/appCRUD/AppCRUDTest.java
    @Test
    void versionNotGreater() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        AppDev appDev = AppDev.builder()
                .id(1L)
                .appId((long)1)
                .subscribers(new ArrayList<>())
                .build();

        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));


        appCRUD.create(1, submitRequest);
        AppInstallerUpdate appInstallerUpdate1 = appInstallerUpdate;
        appInstallerUpdate1.setVersion("0.0.1");
        Assertions.assertThrows(GreaterVersionException.class, () -> {
            appCRUD.updateInstaller((long)1, appInstallerUpdate1, 1);
        });
    }

    @Test
    void updateInstallerVersionSame() throws IOException{
        NotificationData notificationData = NotificationData.builder()
                .id(1L)
                .subjectId(1L)
                .description("Terdapat Update pada Aplikasi ANDA")
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .subscriber(new ArrayList<>())
                .build();

        AppDev appDev = AppDev.builder()
                .id(1L)
                .appId((long)1)
                .subscribers(new ArrayList<>())
                .build();

        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));
        when(appDeveloperRepository.findByAppId(any(Long.class))).thenReturn(Optional.of(appDev));
        when(notificationService.handleNewBroadcast(any(Long.class), any(String.class))).thenReturn(notificationData);

        AppData appDataBfr = appCRUD.create(1, submitRequest);
        appInstallerUpdate.setVersion("1.0.1");
        AppData result = appCRUD.updateInstaller((long)appDataBfr.getId(), appInstallerUpdate, 1);
        AppData appData1 = appDataBfr;
        appData1.setVersion(appInstallerUpdate.getVersion());
        appData1.setInstallerUrl(result.getInstallerUrl());
        Assertions.assertEquals(result, appData1);
    }
=======

    // TODO: fix this
//    @Test
//    void versionNotGreater() throws IOException{
//        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
//            String temp = ".com";
//            return invocation.getArgument(1, String.class) + temp;
//        });
//        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
//            var appData1 = invocation.getArgument(0, AppData.class);
//            appData1.setId((long)1);
//            return appData1;
//        });
//        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));
//
//        appCRUD.create(1, submitRequest);
//        AppInstallerUpdate appInstallerUpdate1 = appInstallerUpdate;
//        appInstallerUpdate1.setVersion("0.0.1");
//        Assertions.assertThrows(GreaterVersionException.class, () -> {
//            appCRUD.updateInstaller((long)1, appInstallerUpdate1, 1);
//        });
//    }
//
//    @Test
//    void updateInstallerVersionSame() throws IOException{
//        NotificationData notificationData = NotificationData.builder()
//                .id(1L)
//                .subjectId(1L)
//                .description("Terdapat Update pada Aplikasi ANDA")
//                .timestamp(new Timestamp(System.currentTimeMillis()))
//                .subscriber(new ArrayList<>())
//                .build();
//
//        AppDev appDev = AppDev.builder()
//                .id(1L)
//                .appId((long)1)
//                .subscribers(new ArrayList<>())
//                .build();
//
//        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
//            String temp = ".com";
//            return invocation.getArgument(1, String.class) + temp;
//        });
//        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
//            var appData1 = invocation.getArgument(0, AppData.class);
//            appData1.setId((long)1);
//            return appData1;
//        });
//        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));
//        when(appDeveloperRepository.findByAppId(any(Long.class))).thenReturn(Optional.of(appDev));
//        when(notificationService.handleNewBroadcast(any(Long.class), any(String.class))).thenReturn(notificationData);
//
//        AppData appDataBfr = appCRUD.create(1, submitRequest);
//        appInstallerUpdate.setVersion("1.0.0");
//        AppData result = appCRUD.updateInstaller((long)appDataBfr.getId(), appInstallerUpdate, 1);
//        AppData appData1 = appDataBfr;
//        appData1.setVersion(appInstallerUpdate.getVersion());
//        appData1.setInstallerUrl(result.getInstallerUrl());
//        Assertions.assertEquals(result, appData1);
//    }
>>>>>>> src/test/java/id/ac/ui/cs/advprog/gamesappsstore/service/appCRUD/AppCRUDTest.java

    @Test
    void deleteAndFound() throws IOException{
        when(storage.uploadFile(any(InputStream.class), anyString())).thenAnswer(invocation -> {
            String temp = ".com";
            return invocation.getArgument(1, String.class) + temp;
        });
        when(appDataRepository.save(any(AppData.class))).thenAnswer(invocation -> {
            var appData1 = invocation.getArgument(0, AppData.class);
            appData1.setId((long)1);
            return appData1;
        });
        when(appDataRepository.findById(any(Long.class))).thenReturn(Optional.of(appData));

        appCRUD.create(1, submitRequest);
        appCRUD.delete((long)1, 1);
        verify(appDataRepository, atLeastOnce()).deleteById(any(Long.class));

    }

    @Test
    void deleteAndNotFound() throws IOException{
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            appCRUD.delete((long)1, 1);
        });
    }
}
