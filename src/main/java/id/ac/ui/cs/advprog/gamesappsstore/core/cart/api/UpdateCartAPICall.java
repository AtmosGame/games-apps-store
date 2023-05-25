package id.ac.ui.cs.advprog.gamesappsstore.core.cart.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.cart.UpdateCartAPIRequest;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor
public class UpdateCartAPICall {
    @Value("${atmos.microservices.payment.url}/api/v1/cart")
    private String endPoint;

    private RestTemplate restTemplate = new RestTemplate();

    public void execute(@NonNull AppData app, @NonNull String username, @NonNull String jwtToken) {
        var headers = getHeaders(jwtToken);
        var body = getBody(app, username);
        var request = new HttpEntity<>(body, headers);
        var response = getResponse(request);
        processResponse(response);
    }

    private HttpHeaders getHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private UpdateCartAPIRequest getBody(AppData app, String username) {
        return new UpdateCartAPIRequest(
                app.getId().toString(),
                app.getName(),
                app.getPrice(),
                username
        );
    }

    private ResponseEntity<String> getResponse(HttpEntity<UpdateCartAPIRequest> request) {
        return restTemplate.exchange(
                endPoint,
                HttpMethod.PUT,
                request,
                String.class
        );
    }

    private void processResponse(ResponseEntity<String> response) {
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ServiceUnavailableException("Error on updating cart");
        }
    }
}
