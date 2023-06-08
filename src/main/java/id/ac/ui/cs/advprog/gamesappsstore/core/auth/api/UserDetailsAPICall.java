package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import id.ac.ui.cs.advprog.gamesappsstore.dto.auth.UserDetailsResponse;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import id.ac.ui.cs.advprog.gamesappsstore.utils.APICallUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor
public class UserDetailsAPICall {
    @Value("${atmos.microservices.auth_admin.url}/v1/user/current")
    private String endPoint;

    private RestTemplate restTemplate = new RestTemplate();

    public User execute(@NonNull String jwtToken) {
        HttpHeaders headers = getHeaders(jwtToken);
        MultiValueMap<String, String> body = getBody();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getResponse(request);
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

    private ResponseEntity<String> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    private User processResponse(ResponseEntity<String> response) {
        var jsonNode = APICallUtils.stringToJsonNode(
                response.getBody(),
                "Error on authentication"
        );
        ((ObjectNode) jsonNode).remove("reportList");
        var userDetailsResponse = APICallUtils.jsonNodeToObject(
                jsonNode,
                UserDetailsResponse.class,
                "Error on authentication"
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
        return UserRole.valueOf(roleString);
    }
}
