package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ExternalAPIException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ShareURLAPICall extends APICall<String, String, String> {
    public static final String ENDPOINT = "https://api.dropboxapi.com/2/sharing/create_shared_link_with_settings";

    private final String accessToken;

    private final String path;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ShareURLAPICall(String accessToken, String path) {
        this.accessToken = accessToken;
        this.path = path;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public String getBody() {
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

    @Override
    public ResponseEntity<String> getResponse(HttpEntity<String> request) {
        return restTemplate.postForEntity(
                ENDPOINT,
                request,
                String.class
        );
    }

    @Override
    public String processResponse(ResponseEntity<String> response) {
        JsonNode json;
        try {
            json = objectMapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new ExternalAPIException("Error on sharing");
        }

        String url = json.get("url").textValue();
        if (url != null) return url;
        else throw new ExternalAPIException("Error on sharing");
    }
}
