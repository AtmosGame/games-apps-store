package id.ac.ui.cs.advprog.gamesappsstore.core.download.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.download.CheckPurchasedRequest;
import id.ac.ui.cs.advprog.gamesappsstore.dto.download.CheckPurchasedResponse;
import id.ac.ui.cs.advprog.gamesappsstore.utils.APICallUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor
public class IsAppOwnedAPICall {
    @Value("${atmos.microservices.payment.url}/check-purchased")
    private String endPoint;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean execute(@NonNull String username,@NonNull Long id, @NonNull String jwtToken) {
        var headers = getHeaders(jwtToken);
        var body = getBody(id.toString(), username);
        var request = new HttpEntity<>(body, headers);
        var response = getResponse(request);
        return processResponse(response);
    }

    private CheckPurchasedRequest getBody(String appId, String username) {
        return new CheckPurchasedRequest(
                appId,
                username
        );
    }

    private HttpHeaders getHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseEntity<String> getResponse(HttpEntity<CheckPurchasedRequest> request) {
        return restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    private boolean processResponse(ResponseEntity<String> response) {
        var jsonNode = APICallUtils.stringToJsonNode(
                response.getBody(),
                "Error on getting ownership data"
        );
        var appDownloadResponse = APICallUtils.jsonNodeToObject(
                jsonNode,
                CheckPurchasedResponse.class,
                "Error on getting ownership data"
        );
        return isAppOwned(appDownloadResponse);
    }

    private boolean isAppOwned(CheckPurchasedResponse checkPurchasedResponse) {
        return checkPurchasedResponse.getIsPurchased();
    }

}
