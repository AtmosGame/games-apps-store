package id.ac.ui.cs.advprog.gamesappsstore.core.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public abstract class APICall<T, K, S> {
    public S execute() {
        HttpHeaders headers = getHeaders();
        T body = getBody();
        HttpEntity<T> request = getRequest(body, headers);
        ResponseEntity<K> response = getResponse(request);
        return processResponse(response);
    }

    public abstract HttpHeaders getHeaders();
    public abstract T getBody();
    public HttpEntity<T> getRequest(T body, HttpHeaders headers) {
        return new HttpEntity<>(body, headers);
    }
    public abstract ResponseEntity<K> getResponse(HttpEntity<T> request);
    public abstract S processResponse(ResponseEntity<K> response);
}
