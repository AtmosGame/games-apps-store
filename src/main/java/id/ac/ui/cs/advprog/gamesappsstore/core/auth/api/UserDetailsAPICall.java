package id.ac.ui.cs.advprog.gamesappsstore.core.auth.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.core.utils.APIUtils;
import id.ac.ui.cs.advprog.gamesappsstore.dto.auth.UserDetailsResponse;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.User;
import id.ac.ui.cs.advprog.gamesappsstore.models.auth.enums.UserRole;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UserDetailsAPICall extends APICall<MultiValueMap<String, String>, UserDetailsResponse, User> {
    @Value("${atmos.microservice.auth_admin.url}/v1/profile/")
    public String endPoint;

    private String jwtToken;

    private RestTemplate restTemplate = new RestTemplate();

    public void setup(@NonNull String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public HttpHeaders getHeaders() {
        if (jwtToken == null) throw new NoSetupException();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public MultiValueMap<String, String> getBody() {
        if (jwtToken == null) throw new NoSetupException();
        return new LinkedMultiValueMap<>();
    }

    @Override
    public ResponseEntity<UserDetailsResponse> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.getForEntity(
                endPoint,
                UserDetailsResponse.class
        );
    }

    @Override
    public User processResponse(ResponseEntity<UserDetailsResponse> response) {
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new ServiceUnavailableException("Error on getting user details");
        }
        UserDetailsResponse userDetailsResponse = response.getBody();
        return User.builder()
                .id(userDetailsResponse.getId())
                .username(userDetailsResponse.getUsername())
                .role(UserRole.valueOf(userDetailsResponse.getRole()))
                .profilePicture(userDetailsResponse.getProfilePicture())
                .active(userDetailsResponse.getActive())
                .build();
    }
}
