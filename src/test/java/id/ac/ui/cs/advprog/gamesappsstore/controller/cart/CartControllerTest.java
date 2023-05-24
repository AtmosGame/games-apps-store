package id.ac.ui.cs.advprog.gamesappsstore.controller.cart;

import id.ac.ui.cs.advprog.gamesappsstore.dto.cart.IsAppInCartResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.service.cart.CartService;
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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CartControllerTest {
    private final String jwtToken = "Bearer uwooogh.sneggs";

    @Mock
    private CartService cartService;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private Authentication authentication;

    private User user;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setup() {
        user = User.builder()
                .id(2)
                .username("ehee")
                .role(UserRole.DEVELOPER)
                .profilePicture("")
                .active(true)
                .build();
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void isAppInCartTest() {
        var expectedStatus = true;
        var expectedResponse = ResponseEntity.ok(new IsAppInCartResponse(expectedStatus));
        Mockito
                .when(cartService.isAppInCart(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(expectedStatus);
        var response = cartController.isAppInCart(1L, jwtToken);
        Assertions.assertEquals(expectedResponse, response);
    }

    @Test
    void addAppToCartTest() {
        Mockito
                .doNothing()
                .when(cartService)
                .addAppToCart(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Assertions.assertDoesNotThrow(() -> {
            cartController.addAppToCart(1L, jwtToken);
        });
    }

    @Test
    void deleteAppFromCart() {
        Mockito
                .doNothing()
                .when(cartService)
                .deleteAppFromCart(
                        ArgumentMatchers.any(Long.class),
                        ArgumentMatchers.anyString()
                );
        Assertions.assertDoesNotThrow(() -> {
            cartController.deleteAppFromCart(1L, jwtToken);
        });
    }
}
