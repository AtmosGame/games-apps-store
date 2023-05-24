package id.ac.ui.cs.advprog.gamesappsstore.core.cart.api;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@NoArgsConstructor
public class DeleteCartAPICall {
    @Value("${atmos.microservices.payment.url}/api/v1/cart/")
    private String endPoint;

    private RestTemplate restTemplate = new RestTemplate();

    public void execute(@NonNull AppData app, @NonNull String jwtToken) {
        var headers = getHeaders(jwtToken);
        var body = getBody();
        var request = new HttpEntity<>(body, headers);
        var response = getResponse(request, app);
        processResponse(response);
    }

    private HttpHeaders getHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private Map<String, String> getBody() {
        return new HashMap<>();
    }

    private ResponseEntity<String> getResponse(HttpEntity<Map<String, String>> request, AppData app) {
        return restTemplate.exchange(
                endPoint + app.getId(),
                HttpMethod.DELETE,
                request,
                String.class
        );
    }

    private void processResponse(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceUnavailableException("Error on deleting cart");
        }
    }
}
