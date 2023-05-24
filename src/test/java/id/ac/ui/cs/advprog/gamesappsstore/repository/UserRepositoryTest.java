package id.ac.ui.cs.advprog.gamesappsstore.repository;

import id.ac.ui.cs.advprog.gamesappsstore.core.auth.api.UserDetailsAPICall;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.repository.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {
    @Mock
    UserDetailsAPICall userDetailsAPICall;

    @InjectMocks
    UserRepository userRepository;

    @Test
    void getByJwtTokenTest() {
        User expectedUser = User.builder()
                .id(2)
                .username("ehee")
                .role(UserRole.DEVELOPER)
                .profilePicture("https://google.com/")
                .active(true)
                .build();
        Mockito.when(userDetailsAPICall.execute("Bearer ABC")).thenReturn(expectedUser);
        User user = userRepository.getByJwtToken("Bearer ABC");
        Assertions.assertEquals(expectedUser, user);
    }
}