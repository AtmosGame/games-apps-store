package id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.Advice;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.CRUDAppData.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {EmptyFormException.class, InvalidVersionException.class,
            LongDescriptionException.class, PriceRangeException.class,
            AppDataDoesNotExistException.class, GreaterVersionException.class})
    public ResponseEntity<Object> orderAndMedicineNotAvailable(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }
}
