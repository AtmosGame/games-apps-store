package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ShareURLAPICall extends APICall<String, String, String> {
    public static final String ENDPOINT = "https://api.dropboxapi.com/2/sharing/create_shared_link_with_settings";

    private String accessToken;

    private String path;

    private RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public void setup(String accessToken, String path) {
        this.accessToken = accessToken;
        this.path = path;
    }

    @Override
    public HttpHeaders getHeaders() {
        if (accessToken == null) throw new NoSetupException();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @Override
    public String getBody() {
        if (accessToken == null) throw new NoSetupException();
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
            throw new ServiceUnavailableException("Error on sharing");
        }

        JsonNode url = json.get("url");
        if (url != null) return url.textValue();
        else throw new ServiceUnavailableException("Error on sharing");
    }
}
