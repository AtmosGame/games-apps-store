package id.ac.ui.cs.advprog.gamesappsstore.exceptions;

public class AppInCartException extends RuntimeException {
    public AppInCartException(Long appId) {
        super(String.format("App with id %s is already in cart", appId));
    }
}
