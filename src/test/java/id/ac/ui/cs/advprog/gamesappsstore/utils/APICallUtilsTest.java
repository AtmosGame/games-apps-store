package id.ac.ui.cs.advprog.gamesappsstore.utils;

import com.fasterxml.jackson.databind.JsonNode;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class APICallUtilsTest {
    @Test
    void jsonStringIsJson() {
        JsonNode node = APICallUtils.stringToJsonNode("{\"name\":\"siapa\"}", "Error");
        Assertions.assertEquals("siapa", node.get("name").textValue());
    }

    @Test
    void jsonStringIsNotJson() {
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            APICallUtils.stringToJsonNode("{", "Error Test");
        });
    }

    @Test
    void jsonNodeIsValidToClass() {
        JsonNode node = APICallUtils.stringToJsonNode("{\"name\":\"siapa\"}", "Error");
        var object = APICallUtils.jsonNodeToObject(node, JsonNodeConversionTestClass.class, "Error");
        Assertions.assertEquals("siapa", object.name);
    }

    @Test
    void jsonNodeIsNotValidToClass() {
        JsonNode node = APICallUtils.stringToJsonNode("{\"namax\":\"siapa\"}", "Error");
        Assertions.assertThrows(ServiceUnavailableException.class, () -> {
            APICallUtils.jsonNodeToObject(node, JsonNodeConversionTestClass.class, "Error");
        });
    }
}

class JsonNodeConversionTestClass {
    public String name;
}
