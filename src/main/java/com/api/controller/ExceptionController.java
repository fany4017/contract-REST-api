package com.api.controller;

import com.api.code.DefaultResponse;
import com.api.code.ResponseMessageCode;
import com.api.code.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.UnexpectedTypeException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity handleRuntimeException(RuntimeException e) {
        return new ResponseEntity(DefaultResponse.res(StatusCode.BAD_REQUEST,
                ResponseMessageCode.RuntimeException), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity handleUnexpectedTypeException(RuntimeException e) {
        return new ResponseEntity(DefaultResponse.res(StatusCode.BAD_REQUEST,
                ResponseMessageCode.UnexpectedTypeException), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(RuntimeException e) {
        return new ResponseEntity(DefaultResponse.res(StatusCode.BAD_REQUEST,
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
