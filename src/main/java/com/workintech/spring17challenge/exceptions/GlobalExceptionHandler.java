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
    public ResponseEntity<ApiErrorResponse> handleException(ApiException apiException){
        log.error("Api Exception occured! Exception details : " , apiException.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(apiException.getHttpStatus().value(),apiException.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse,apiException.getHttpStatus());
    }
    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception){
        log.error("apiexception occured! Exception details: ", exception.getMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
