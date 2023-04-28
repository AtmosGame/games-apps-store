package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.auth.UserDetailsResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import lombok.NonNull;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UserDetailsAPICall {
    private static final String ENDPOINT = "http://localhost:8081/v1/profile/";

    private RestTemplate restTemplate = new RestTemplate();

    public User execute(@NonNull String jwtToken) {
        HttpHeaders headers = getHeaders(jwtToken);
        MultiValueMap<String, String> body = getBody();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<UserDetailsResponse> response = getResponse(request);
        return processResponse(response);
    }

    private HttpHeaders getHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private MultiValueMap<String, String> getBody() {
        return new LinkedMultiValueMap<>();
    }

    private ResponseEntity<UserDetailsResponse> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.exchange(
                ENDPOINT,
                HttpMethod.GET,
                request,
                UserDetailsResponse.class
        );
    }

    private User processResponse(ResponseEntity<UserDetailsResponse> response) {
        UserDetailsResponse userDetailsResponse = response.getBody();
        if (response.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException("Invalid request");
        }
        if (!response.getStatusCode().is2xxSuccessful() || userDetailsResponse == null) {
            throw new ServiceUnavailableException("Error on getting user details");
        }
        return User.builder()
                .id(userDetailsResponse.getId())
                .username(userDetailsResponse.getUsername())
                .role(stringToUserRole(userDetailsResponse.getRole()))
                .profilePicture(userDetailsResponse.getProfilePicture())
                .active(userDetailsResponse.getActive())
                .build();
    }

    private UserRole stringToUserRole(String roleString) {
        switch (roleString) {
            case "admin" -> {
                return UserRole.ADMIN;
            }
            case "developer" -> {
                return UserRole.DEVELOPER;
            }
            default -> {
                return UserRole.USER;
            }
        }
    }
}
