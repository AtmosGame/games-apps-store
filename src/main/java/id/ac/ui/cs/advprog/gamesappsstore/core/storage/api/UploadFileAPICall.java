package id.ac.ui.cs.advprog.gamesappsstore.core.storage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.core.api.APICall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ExternalAPIException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.PayloadTooLargeException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;

public class UploadFileAPICall extends APICall<byte[], String, String> {
    public static final String ENDPOINT = "https://content.dropboxapi.com/2/files/upload";

    private final String accessToken;
    private final String path;
    private final InputStream file;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UploadFileAPICall(String accessToken, String path, InputStream file) {
        this.accessToken = accessToken;
        this.path = path;
        this.file = file;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Dropbox-API-Arg", "{"
                + "\"autorename\":false,"
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
        try {
            return file.readAllBytes();
        } catch (Exception e) {
            throw new ServerException("Error on file conversion");
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
            throw new ExternalAPIException("Error on file upload");
        }

        String storagePath = json.get("path_display").textValue();
        if (storagePath != null) return storagePath;

        String errorMessage = json.get("error").get(".tag").textValue();
        if (errorMessage.equals("payload_too_large")) throw new PayloadTooLargeException("Payload is too large");
        else throw new ExternalAPIException("Error on file upload");
    }
}
