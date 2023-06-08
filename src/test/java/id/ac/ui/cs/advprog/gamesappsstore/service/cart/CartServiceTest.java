package id.ac.ui.cs.advprog.gamesappsstore.service.cart;

import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.DeleteCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.IsAppInCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.cart.api.UpdateCartAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.cart.AppInCartException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.enums.VerificationStatus;
import id.ac.ui.cs.advprog.gamesappsstore.repository.app.AppDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    private final String jwtToken = "Bearer ehee.tenandayo";

    @Mock
    private IsAppInCartAPICall isAppInCartAPICall;
    @Mock
    private DeleteCartAPICall deleteCartAPICall;
    @Mock
    private UpdateCartAPICall updateCartAPICall;

    @InjectMocks
    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private AppDataRepository appDataRepository;

    @BeforeEach
    void setup() {
        AppData appData = AppData.builder()
                .id(1L)
                .name("App 1")
                .userId(2)
                .imageUrl("https://image.com/app1")
                .installerUrl("https://storage.com/app1")
                .description("The first app")
                .version("1.0.0")
                .price(10000d)
                .verificationStatus(VerificationStatus.UNVERIFIED)
                .verificationAdminId(null)
                .verificationDate(null)
                .build();
        appDataRepository.save(appData);
    }

    @Test
    void isAppInCartTrueTest() {
        var expectedInCart = true;
        Mockito
                .when(isAppInCartAPICall.execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(expectedInCart);
        var inCart = cartService.isAppInCart(1L, jwtToken);
        Assertions.assertEquals(expectedInCart, inCart);
    }

    @Test
    void isAppInCartFalseTest() {
        var expectedInCart = false;
        Mockito
                .when(isAppInCartAPICall.execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(expectedInCart);
        var inCart = cartService.isAppInCart(1L, jwtToken);
        Assertions.assertEquals(expectedInCart, inCart);
    }

    @Test
    void addAppToCartSuccessTest() {
        Mockito
                .doNothing()
                .when(updateCartAPICall)
                .execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
        Mockito
                .when(isAppInCartAPICall.execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(false);
        Assertions.assertDoesNotThrow(() -> {
            cartService.addAppToCart(1L, "ehee", jwtToken);
        });
    }

    @Test
    void addAppToCartExistsTest() {
        Mockito
                .when(isAppInCartAPICall.execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(true);
        Assertions.assertThrows(AppInCartException.class, () -> {
            cartService.addAppToCart(1L, "ehee", jwtToken);
        });
    }

    @Test
    void addAppToCartNotSuccessTest() {
        Mockito
                .doThrow(ServiceUnavailableException.class)
                .when(updateCartAPICall)
                .execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString()
                );
        Mockito
                .when(isAppInCartAPICall.execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                ))
                .thenReturn(false);
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            cartService.addAppToCart(1L, "ehee", jwtToken);
        });
    }

    @Test
    void deleteAppFromCartSuccessTest() {
        Mockito
                .doNothing()
                .when(deleteCartAPICall)
                .execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                );
        Assertions.assertDoesNotThrow(() -> {
            cartService.deleteAppFromCart(1L, jwtToken);
        });
    }

    @Test
    void deleteAppFromCartNotSuccessTest() {
        Mockito
                .doThrow(ServiceUnavailableException.class)
                .when(deleteCartAPICall)
                .execute(
                        ArgumentMatchers.any(AppData.class),
                        ArgumentMatchers.anyString()
                );
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            cartService.deleteAppFromCart(1L, jwtToken);
        });
    }

    @Test
    void appNotFoundTest() {
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            cartService.isAppInCart(101L, jwtToken);
        });
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            cartService.deleteAppFromCart(101L, jwtToken);
        });
        Assertions.assertThrows(AppDataDoesNotExistException.class, () -> {
            cartService.addAppToCart(101L, "ehee", jwtToken);
        });
    }
}
