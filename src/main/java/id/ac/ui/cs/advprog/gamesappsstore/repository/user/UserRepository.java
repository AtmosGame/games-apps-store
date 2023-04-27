package id.ac.ui.cs.advprog.gamesappsstore.repository.user;

import id.ac.ui.cs.advprog.gamesappsstore.models.user.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User getByUsername(String username) {
        // TODO: Throw UserNotFoundException
        return null;
    }
    public User findByJWT(String jwt) {
        return null;
    }
}
