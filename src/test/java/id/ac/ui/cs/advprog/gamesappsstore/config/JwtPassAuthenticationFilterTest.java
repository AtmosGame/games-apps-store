package id.ac.ui.cs.advprog.gamesappsstore.config;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.auth.UserNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.utils.ControllerUtils;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class JwtPassAuthenticationFilterTest {
    @Mock
    private UserDetailsService userDetailsService;
    private JwtPassAuthenticationFilter filter;

    @BeforeEach
    void setup() {
        filter = new JwtPassAuthenticationFilter(userDetailsService);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    void validUserTest() throws IOException, ServletException {
        UserDetails expectedUser = User.builder()
                .id(1)
                .username("ehee")
                .active(true)
                .role(UserRole.USER)
                .build();
        Mockito
                .when(userDetailsService.loadUserByUsername(any(String.class)))
                .thenReturn(expectedUser);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer test.ehe");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        User user = ControllerUtils.getCurrentUser();
        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void invalidUserTest() throws IOException, ServletException {
        Mockito
                .when(userDetailsService.loadUserByUsername(any(String.class)))
                .thenThrow(UserNotFoundException.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer test.ehe");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        User user = ControllerUtils.getCurrentUser();
        Assertions.assertNull(user);
    }

    @Test
    void noJwtTokenTest() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilterInternal(request, response, chain);

        User user = ControllerUtils.getCurrentUser();
        Assertions.assertNull(user);
    }

    @Test
    void authenticationIsNotNullTest() throws IOException, ServletException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        var authentication = new UsernamePasswordAuthenticationToken(
                null,
                null,
                null
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filter.doFilterInternal(request, response, chain);

        User user = ControllerUtils.getCurrentUser();
        Assertions.assertNull(user);
    }
}
