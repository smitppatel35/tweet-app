package com.tweetapp.exception;

import com.tweetapp.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse("Validation Error", details);
        log.debug("[BAD_REQUEST] [Validation Error] Timestamp: {}, Details: {}", errorResponse.getTimestamp(), errorResponse.getDetails());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ex.printStackTrace();

        ErrorResponse errorResponse = new ErrorResponse("Server Error", details);
        log.debug("[INTERNAL_SERVER_ERROR] [Server Error] Timestamp: {}, Details: {}", errorResponse.getTimestamp(), errorResponse.getDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse("Record Not Found", details);
        log.debug("[NOT_FOUND] [Record Not Found] Timestamp: {}, Details: {}", errorResponse.getTimestamp(), errorResponse.getDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse("Record already exists", details);
        log.debug("[CONFLICT] [Record already exists] Timestamp: {}, Details: {}", errorResponse.getTimestamp(), errorResponse.getDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmptyResourceContentException.class)
    public ResponseEntity<Object> handleEmptyResourceContentException(EmptyResourceContentException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse errorResponse = new ErrorResponse("Empty Record", details);
        log.debug("[NO_CONTENT] [Empty Record] Timestamp: {}, Details: {}", errorResponse.getTimestamp(), errorResponse.getDetails());

        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }
}
