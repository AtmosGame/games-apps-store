package id.ac.ui.cs.advprog.gamesappsstore.exceptions.advice;

import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.*;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.AppDevDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberAlreadySubscribeExepction;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberDoesNotExistException;
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
            AppDataDoesNotExistException.class, GreaterVersionException.class,
            AppDevDoesNotExistException.class, SubscriberAlreadySubscribeExepction.class,
            SubscriberDoesNotExistException.class})
    public ResponseEntity<Object> errorException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }
}
