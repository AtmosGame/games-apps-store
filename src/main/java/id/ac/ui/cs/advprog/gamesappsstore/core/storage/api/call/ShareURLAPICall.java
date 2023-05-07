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
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class ShareURLAPICall implements StorageAPICall {
    private final String endPoint;

    private String accessToken;

    private String path;

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public void setup(@NonNull String accessToken, @NonNull String path) {
        this.accessToken = accessToken;
        this.path = path;
    }

    public String execute() {
        if (accessToken == null) throw new NoSetupException();
        HttpHeaders headers = getHeaders();
        String body = getBody();
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = getResponse(request);
        return processResponse(response);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String getBody() {
        return "{"
            + "\"path\":\"" + path + "\","
            + "\"settings\": {"
            + "\"access\": \"viewer\","
            + "\"allow_download\": true,"
            + "\"audience\": \"public\","
            + "\"requested_visibility\": \"public\""
            + "}"
            + "}";
    }

    private ResponseEntity<String> getResponse(HttpEntity<String> request) {
        return restTemplate.postForEntity(
                endPoint,
                request,
                String.class
        );
    }

    private String processResponse(ResponseEntity<String> response) {
        JsonNode json;
        try {
            json = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new ServiceUnavailableException("Error on sharing");
        }

        JsonNode url = json.get("url");
        if (url != null) return url.textValue();
        else throw new ServiceUnavailableException("Error on sharing");
    }
}
