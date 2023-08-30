package com.logicea.cards.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class ExceptionControllerAdvise extends ResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                               HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(Map.of(
                "type", "https://mikehenry-maina.com/problems/kotlin-playgroung/constraint-violation",
                "title", ex.getBindingResult().getAllErrors().stream().findFirst(),
                "status", HttpStatus.BAD_REQUEST,
                "message", ex.getMessage(),
                "params", ex.getBindingResult().getAllErrors().stream().findFirst()
        ), HttpStatus.BAD_REQUEST);
    }
}
