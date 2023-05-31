package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.controller.app.AppDetailController;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailFullResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.app.AppDetailValidationResponse;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDetailServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

class AppDetailControllerTest {

    @Mock
    private AppDetailServiceImpl appDetailService;

    @InjectMocks
    private AppDetailController appDetailController;

    AppDetailValidationRequest appDetailValidationRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAppsById() {
        Long appId = 1L;

        AppDetailFullResponse expectedResponse = AppDetailFullResponse.builder()
                .id(appId)
                .name("Test App")
                .imageUrl("http://example.com/test.png")
                .description("This is a test app")
                .installerUrl("http://example.com/test.apk")
                .version("1.0.0")
                .price(9.99)
                .verificationStatus(null)
                .verificationAdminId(123)
                .verificationDate(new Date())
                .build();

        Mockito.when(appDetailService.getAppDetailbyId(appId)).thenReturn(expectedResponse);

        ResponseEntity<AppDetailFullResponse> response = appDetailController.getAppsByKeyword(appId);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }

    @Test
    void testValidateApp() {
        AppDetailValidationRequest request = AppDetailValidationRequest.builder()
                .id(1L)
                .name("testApp")
                .price(9.99)
                .build();

        AppDetailValidationResponse expectedResponse = new AppDetailValidationResponse(true);

        Mockito.when(appDetailService.validateApp(request)).thenReturn(expectedResponse);

        ResponseEntity<AppDetailValidationResponse> response = appDetailController.validate(request);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedResponse, response.getBody());
    }
}
