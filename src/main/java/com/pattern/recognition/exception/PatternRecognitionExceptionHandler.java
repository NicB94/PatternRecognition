package com.pattern.recognition.exception;

import com.pattern.recognition.data.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PatternRecognitionExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { InvalidParametersException.class})
    protected ResponseEntity<ApiErrorResponse>  handleInvalidParameterException(InvalidParametersException ex){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(ex.getMessage()));
    }
}
