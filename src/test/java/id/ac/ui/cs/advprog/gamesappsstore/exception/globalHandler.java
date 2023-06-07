package id.ac.ui.cs.advprog.gamesappsstore.exception;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.advice.GlobalExceptionHandler;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.AppDataDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.ErrorTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("unchecked")

class globalHandler {

    @Mock
    private WebRequest webRequest;

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleTypeMismatchTest() {
        String errorMessage = "Error message describing the issue";
        HttpInputMessage inputMessage=  mock(HttpInputMessage.class);
        HttpMessageNotReadableException exception = new HttpMessageNotReadableException(errorMessage, inputMessage);

        ResponseEntity<Object> responseEntity = exceptionHandler.handleTypeMismatch(exception, webRequest);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedMessage = "Format input tidak valid. Pastikan semua bidang input berjenis yang benar.";
        String actualMessage = ((Map<String, Object>) responseEntity.getBody()).get("message").toString();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void handleError() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

        AppDataDoesNotExistException exception = new AppDataDoesNotExistException();

        ResponseEntity<Object> responseEntity = exceptionHandler.errorException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        String expectedMessage = "Tidak App dengan id tersebut";
        ErrorTemplate errorTemplate = (ErrorTemplate) responseEntity.getBody();
        Assertions.assertEquals(expectedMessage, errorTemplate.message());

    }
}
