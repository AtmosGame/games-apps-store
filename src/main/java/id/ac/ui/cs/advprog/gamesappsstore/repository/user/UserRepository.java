package id.ac.ui.cs.advprog.gamesappsstore.repository.user;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserByIdAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private UserDetailsAPICall userDetailsAPICall;
    @Autowired
    private UserByIdAPICall userByIdAPICall;

    public User getByJwtToken(String jwtToken) {
        return userDetailsAPICall.execute(jwtToken);
    }

    public User getById(Integer id) {
        return userByIdAPICall.execute(id);
    }
}
