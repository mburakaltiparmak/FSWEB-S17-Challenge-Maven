package com.workintech.spring17challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException exception) {
        log.error("apiexception occured! Exception details: ", exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(exception.getHttpStatus().value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, exception.getHttpStatus());

    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
        log.error("apiexception occured! Exception details: ", exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);

    }


}
