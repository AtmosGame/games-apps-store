package id.ac.ui.cs.advprog.gamesappsstore.repository.user;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private UserDetailsAPICall userDetailsAPICall = new UserDetailsAPICall();

    public User getByJwtToken(String jwtToken) {
        return userDetailsAPICall.execute(jwtToken);
    }
}
