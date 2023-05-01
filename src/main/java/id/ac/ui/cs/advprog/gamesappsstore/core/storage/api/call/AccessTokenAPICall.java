package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api.call;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class AccessTokenAPICall implements StorageAPICall {
    private final String endPoint;

    private String refreshToken;
    private String appKey;
    private String appSecret;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setup(@NonNull String refreshToken, @NonNull String appKey, @NonNull String appSecret) {
        this.refreshToken = refreshToken;
        this.appKey = appKey;
        this.appSecret = appSecret;
    }

    public String execute() {
        if (refreshToken == null) throw new NoSetupException();
        HttpHeaders headers = getHeaders();
        MultiValueMap<String, String> body = getBody();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getResponse(request);
        return processResponse(response);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> getBody() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("refresh_token", this.refreshToken);
        request.add("grant_type", "refresh_token");
        request.add("client_id", this.appKey);
        request.add("client_secret", this.appSecret);
        return request;
    }

    private String processResponse(ResponseEntity<String> response) {
        String jsonString = response.getBody();
        JsonNode json;
        try {
            json = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new ServiceUnavailableException("Error on storage authentication");
        }

        JsonNode accessToken = json.get("access_token");
        if (accessToken != null) return accessToken.textValue();
        else throw new ServiceUnavailableException("Error on storage authentication");
    }

    private ResponseEntity<String> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.postForEntity(
                endPoint,
                request,
                String.class
        );
    }
}
