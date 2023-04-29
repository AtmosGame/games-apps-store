package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.auth.UserDetailsResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.utils.APICallUtils;
import lombok.NonNull;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UserByIdAPICall {
    private static final String ENDPOINT = "http://localhost:8000/users/";

    private RestTemplate restTemplate = new RestTemplate();

    public User execute(@NonNull Integer id) {
        HttpHeaders headers = getHeaders();
        MultiValueMap<String, String> body = getBody();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getResponse(request, id);
        return processResponse(response);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private MultiValueMap<String, String> getBody() {
        return new LinkedMultiValueMap<>();
    }

    private ResponseEntity<String> getResponse(HttpEntity<MultiValueMap<String, String>> request, Integer id) {
        return restTemplate.exchange(
                ENDPOINT + id,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    private User processResponse(ResponseEntity<String> response) {
        var jsonNode = APICallUtils.stringToJsonNode(
                response.getBody(),
                "Error on storage authentication"
        );
        var userDetailsResponse = APICallUtils.jsonNodeToObject(
                jsonNode,
                UserDetailsResponse.class,
                "Error on storage authentication"
        );
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
