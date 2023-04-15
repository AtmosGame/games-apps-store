package id.ac.ui.cs.advprog.gamesappsstore.exceptions;

public class AppDataNotFoundException extends RuntimeException {
    public AppDataNotFoundException(Long id) {
        super("AppData with id " + id +" not found");
    }
}
