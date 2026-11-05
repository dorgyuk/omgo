package io.github.dorgyuk.omgo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.dorgyuk.omgo.exception.dto.ErrorResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(
        AppException exception
    ) {
        ErrorCode errorCode = exception.getErrorCode();
        
        ErrorResponse response = new ErrorResponse(
            errorCode,
            exception.getMessage()
        );

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(response);
    }
}
