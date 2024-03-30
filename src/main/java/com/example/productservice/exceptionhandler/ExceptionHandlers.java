package com.example.productservice.exceptionhandler;

import com.example.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundExceptionHandler() {

        ExceptionDto dto = new ExceptionDto("Unhandled exception encountered");
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
