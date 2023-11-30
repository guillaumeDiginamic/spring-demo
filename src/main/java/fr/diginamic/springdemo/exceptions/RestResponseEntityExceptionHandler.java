package fr.diginamic.springdemo.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ExceptionHandler({ AnomalieException.class})
    protected ResponseEntity<String> traiterErreurs(AnomalieException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
