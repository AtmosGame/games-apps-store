package id.ac.ui.cs.advprog.gamesappsstore.service.cart;

public interface CartService {
    boolean isAppInCart(Long appId, String jwtToken);
    void addAppToCart(Long appId, String username, String jwtToken);
    void deleteAppFromCart(Long appId, String jwtToken);
}
