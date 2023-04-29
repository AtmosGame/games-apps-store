package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.PayloadTooLargeException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

public class UploadFileAPICall extends APICall<byte[], String, String> {
    public static final String ENDPOINT = "https://content.dropboxapi.com/2/files/upload";

    private String accessToken;
    private String path;
    private InputStream file;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void setup(String accessToken, String path, InputStream file) {
        this.accessToken = accessToken;
        this.path = path;
        this.file = file;
    }

    @Override
    public HttpHeaders getHeaders() {
        if (accessToken == null) throw new NoSetupException();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Dropbox-API-Arg", "{"
                + "\"autorename\":true,"
                + "\"mode\":\"add\","
                + "\"mute\":false,"
                + "\"path\":" + "\"" + path + "\","
                + "\"strict_conflict\":false"
                + "}"
        );
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return headers;
    }

    @Override
    public byte[] getBody() {
        if (accessToken == null) throw new NoSetupException();
        try {
            return file.readAllBytes();
        } catch (Exception e) {
            throw new ServiceUnavailableException("Error on file conversion");
        }
    }

    @Override
    public ResponseEntity<String> getResponse(HttpEntity<byte[]> request) {
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
            throw new ServiceUnavailableException("Error on file upload");
        }

        JsonNode storagePath = json.get("path_display");
        if (storagePath != null) return storagePath.textValue();

        String errorMessage = json.get("error").get(".tag").textValue();
        if (errorMessage.equals("payload_too_large")) throw new PayloadTooLargeException("Payload is too large");
        else throw new ServiceUnavailableException("Error on file upload");
    }
}
