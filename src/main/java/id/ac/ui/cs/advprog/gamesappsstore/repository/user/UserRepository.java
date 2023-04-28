package id.ac.ui.cs.advprog.gamesappsstore.repository.user;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public User getByUsername(String username) {
        // TODO: Throw UserNotFoundException
        return null;
    }
    public User getByJwtToken(String jwtToken) {
        UserDetailsAPICall userDetailsAPICall = new UserDetailsAPICall();
        userDetailsAPICall.setup(jwtToken);
        return userDetailsAPICall.execute();
    }
}
