package code.tofu.useSecurity.controller;

import code.tofu.useSecurity.exception.InvalidJWTException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import code.tofu.useSecurity.exception.CustomControllerException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomControllerException.class)
    public ResponseEntity<String> handleCustomControllerException(CustomControllerException e) {
        log.error("Global Exception Caught:",e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    //does this catch at servelet/filterlevel?
//    @ExceptionHandler(InvalidJWTException.class)
//    public ResponseEntity<String> handleInvalidJWTException(InvalidJWTException e) {
//        log.error("Global Exception Caught:",e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//    }

}