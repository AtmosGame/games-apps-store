package id.ac.ui.cs.advprog.gamesappsstore.exceptions.auth;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
