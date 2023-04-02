package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.constants.DropboxKeys;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ExternalAPIException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AccessTokenAPICall extends APICall<MultiValueMap<String, String>, String, String> {
    public static final String ENDPOINT = "https://api.dropbox.com/oauth2/token";

    private final DropboxKeys dropboxKeys;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AccessTokenAPICall(DropboxKeys dropboxKeys) {
        this.dropboxKeys = dropboxKeys;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Override
    public MultiValueMap<String, String> getBody() {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("refresh_token", dropboxKeys.refreshToken);
        request.add("grant_type", "refresh_token");
        request.add("client_id", dropboxKeys.appKey);
        request.add("client_secret", dropboxKeys.appSecret);
        return request;
    }

    @Override
    public String processResponse(ResponseEntity<String> response) {
        String jsonString = response.getBody();
        JsonNode json;
        try {
            json = objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new ExternalAPIException("Error on storage authentication");
        }

        String accessToken = json.get("access_token").textValue();
        if (accessToken != null) return accessToken;
        else throw new ExternalAPIException("Error on storage authentication");
    }

    @Override
    public ResponseEntity<String> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.postForEntity(
                ENDPOINT,
                request,
                String.class
        );
    }
}
