package id.ac.ui.cs.advprog.gamesappsstore.controller.cart;

import id.ac.ui.cs.advprog.gamesappsstore.dto.cart.IsAppInCartResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('cart:read')")
    public ResponseEntity<IsAppInCartResponse> isAppInCart(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        boolean status = cartService.isAppInCart(id, jwtToken);
        return ResponseEntity.ok(new IsAppInCartResponse(status));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('cart:add')")
    public ResponseEntity<String> addAppToCart(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        String username = getCurrentUser().getUsername();
        cartService.addAppToCart(id, username, jwtToken);
        return ResponseEntity.ok("Added app with id " + id + " to cart");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('cart:delete')")
    public ResponseEntity<String> deleteAppFromCart(
            @PathVariable Long id,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String jwtToken) {
        cartService.deleteAppFromCart(id, jwtToken);
        return ResponseEntity.ok("Deleted app with id " + id + " to cart");
    }

    private static User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
