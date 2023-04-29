package id.ac.ui.cs.advprog.gamesappsstore.repository.user;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserByIdAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private UserDetailsAPICall userDetailsAPICall = new UserDetailsAPICall();
    private UserByIdAPICall userByIdAPICall = new UserByIdAPICall();

    public User getByJwtToken(String jwtToken) {
        return userDetailsAPICall.execute(jwtToken);
    }

    public User getById(Integer id) {
        return userByIdAPICall.execute(id);
    }
}
