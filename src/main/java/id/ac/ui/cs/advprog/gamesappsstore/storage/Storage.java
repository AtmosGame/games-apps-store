package id.ac.ui.cs.advprog.gamesappsstore.storage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ExternalAPIException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.PayloadTooLargeException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;

public class Storage {
    private static final String ACCESS_TOKEN_ENDPOINT = "https://api.dropbox.com/oauth2/token";
    private static final String UPLOAD_ENDPOINT = "https://content.dropboxapi.com/2/files/upload";

    private final String refreshToken;
    public final String appKey;
    private final String appSecret;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public Storage(String refreshToken, String appKey, String appSecret) {
        this.refreshToken = refreshToken;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    private String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>();
            requestMap.add("refresh_token", refreshToken);
            requestMap.add("grant_type", "refresh_token");
            requestMap.add("client_id", appKey);
            requestMap.add("client_secret", appSecret);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestMap, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    Storage.ACCESS_TOKEN_ENDPOINT,
                    request,
                    String.class
            );

            String jsonString = response.getBody();
            JsonNode json = objectMapper.readTree(jsonString);

            String accessToken = json.get("access_token").textValue();
            if (accessToken != null) {
                return accessToken;
            } else {
                throw new ExternalAPIException("Error on storage authentication");
            }
        } catch (Exception e) {
            throw new ExternalAPIException("Error on storage authentication");
        }
    }

    public String uploadFile(InputStream fileInputStream, String path) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getAccessToken());
            headers.set("Dropbox-API-Arg", "{"
                            + "\"autorename\":false,"
                            + "\"mode\":\"add\","
                            + "\"mute\":false,"
                            + "\"path\":" + "\"" + path + "\","
                            + "\"strict_conflict\":false"
                            + "}"
            );
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            HttpEntity<byte[]> request = new HttpEntity<>(fileInputStream.readAllBytes(), headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    Storage.UPLOAD_ENDPOINT,
                    request,
                    String.class
            );

            String jsonString = response.getBody();
            JsonNode json = objectMapper.readTree(jsonString);

            String storagePath = json.get("path_display").textValue();
            if (storagePath != null) {
                return storagePath;
            }

            String errorMessage = json.get("error").get(".tag").textValue();

            if (errorMessage.equals("payload_too_large")) {
                throw new PayloadTooLargeException("Payload is too large");
            } else {
                throw new ExternalAPIException("Error on file upload");
            }
        } catch (IOException e) {
            throw new ServerException("IO Error");
        }
    }
}
