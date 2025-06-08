package code.tofu.useSecurity.controller;

import code.tofu.useSecurity.exception.InvalidJWTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import code.tofu.useSecurity.exception.CustomControllerException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // NOTE: Not a good practice to return details about exception - return generic error codes instead, for security reasons
    // NOTE: Not a good practice to return 404 on authenticated routes since it may reveal your application api paths
    @ExceptionHandler(CustomControllerException.class)
    public ResponseEntity<String> handleCustomControllerException(CustomControllerException e) {
        log.error("CustomControllerException Caught at Advice:",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Thrown Exception:" + e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSQLException(SQLException e) {
        log.error("SQL Exception Caught:",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("SQL Exception:" + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        log.error("Generic Exception Caught:",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

}