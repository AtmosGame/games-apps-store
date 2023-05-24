package id.ac.ui.cs.advprog.gamesappsstore.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;

public class APICallUtils {
    private APICallUtils() {}

    public static JsonNode stringToJsonNode(String jsonString, String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jsonString);
        } catch (Exception e) {
            throw new ServiceUnavailableException(errorMessage);
        }
    }

    public static <T> T jsonNodeToObject(JsonNode jsonNode, Class<T> valueType, String errorMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.treeToValue(jsonNode, valueType);
        } catch (Exception e) {
            throw new ServiceUnavailableException(errorMessage);
        }
    }
}
