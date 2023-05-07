package id.ac.ui.cs.advprog.gamesappsstore.core.cart.api;

import id.ac.ui.cs.advprog.gamesappsstore.dto.cart.GetCartAPIResponse;
import id.ac.ui.cs.advprog.gamesappsstore.dto.cart.GetCartAPIResponseCartDetails;
import id.ac.ui.cs.advprog.gamesappsstore.models.app.AppData;
import id.ac.ui.cs.advprog.gamesappsstore.utils.APICallUtils;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor
public class IsAppInCartAPICall {
    @Value("${atmos.microservices.payment.url}/cart")
    private String endPoint;

    private RestTemplate restTemplate = new RestTemplate();

    public boolean execute(@NonNull AppData app, @NonNull String jwtToken) {
        var headers = getHeaders(jwtToken);
        var body = getBody();
        var request = new HttpEntity<>(body, headers);
        var response = getResponse(request);
        return processResponse(response, app);
    }

    private HttpHeaders getHeaders(String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private MultiValueMap<String, String> getBody() {
        return new LinkedMultiValueMap<>();
    }

    private ResponseEntity<String> getResponse(HttpEntity<MultiValueMap<String, String>> request) {
        return restTemplate.exchange(
                endPoint,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    private boolean processResponse(ResponseEntity<String> response, AppData app) {
        var jsonNode = APICallUtils.stringToJsonNode(
                response.getBody(),
                "Error on getting cart data"
        );
        var getCartResponse = APICallUtils.jsonNodeToObject(
                jsonNode,
                GetCartAPIResponse.class,
                "Error on getting cart data"
        );
        return isAppInCart(app, getCartResponse);
    }

    private boolean isAppInCart(AppData app, GetCartAPIResponse getCartAPIResponse) {
        for (GetCartAPIResponseCartDetails cartDetails : getCartAPIResponse.getCartDetailsData()) {
            Long appId = Long.parseLong(cartDetails.getAppId());
            if (appId.equals(app.getId())) {
                return true;
            }
        }
        return false;
    }
}
