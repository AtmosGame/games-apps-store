package id.ac.ui.cs.advprog.gamesappsstore.exceptions;

public class ForbiddenMethodCall extends RuntimeException {
    public ForbiddenMethodCall() {
        super();
    }

    public ForbiddenMethodCall(String message) {
        super(message);
    }
}
