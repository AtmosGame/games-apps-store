package id.ac.ui.cs.advprog.gamesappsstore.utils;

import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class ControllerUtils {
    private ControllerUtils() {}

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
