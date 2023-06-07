package id.ac.ui.cs.advprog.gamesappsstore.exceptions.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ForbiddenMethodCall;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.NoSetupException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.ServiceUnavailableException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.UnauthorizedException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.auth.UserNotFoundException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.cart.AppInCartException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.crudapp.*;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.AppDevDoesNotExistException;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberAlreadySubscribeExepction;
import id.ac.ui.cs.advprog.gamesappsstore.exceptions.notification.SubscriberDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {EmptyFormException.class, InvalidVersionException.class,
            LongDescriptionException.class, PriceRangeException.class,
            AppDataDoesNotExistException.class, GreaterVersionException.class,
            AppDevDoesNotExistException.class, SubscriberAlreadySubscribeExepction.class,
            SubscriberDoesNotExistException.class, UserNotFoundException.class,
            AppInCartException.class, PayloadTooLargeException.class, ForbiddenMethodCall.class,
            NoSetupException.class, ServiceUnavailableException.class, UnauthorizedException.class})
    public ResponseEntity<Object> errorException(Exception exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(baseException, badRequest);
    }

        @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
        public ResponseEntity<Object> handleTypeMismatch(HttpMessageNotReadableException ex, WebRequest request) {
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("message", "Format input tidak valid. Pastikan semua bidang input berjenis yang benar.");
            body.put("httpStatus", HttpStatus.BAD_REQUEST.value());
            body.put("timestamp", new Date());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }

}
