package id.ac.ui.cs.advprog.gamesappsstore.controller.app;

import id.ac.ui.cs.advprog.gamesappsstore.dto.download.AppDownloadResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.service.app.AppDownloadServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class AppDownloadControllerTest {
    private final String jwtToken = "Bearer wow.xd";

    @Mock
    private AppDownloadServiceImpl appDownloadService;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    private User user;

    @InjectMocks
    private DownloadController downloadController;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(2)
                .username("bambang")
                .role(UserRole.USER)
                .profilePicture("")
                .active(true)
                .build();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getDownloadUrlTest() {
        AppDownloadResponse expectedUrl = new AppDownloadResponse("a");
        var expectedResponse = ResponseEntity.ok(expectedUrl);
        Mockito
                .when(appDownloadService.getDownloadUrl(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(expectedUrl);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        var response = downloadController.getDownloadUrl(1L, jwtToken);
        Assertions.assertEquals(expectedResponse, response);
    }
}
