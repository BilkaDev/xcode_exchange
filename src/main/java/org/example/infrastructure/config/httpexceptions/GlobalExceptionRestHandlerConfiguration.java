package org.example.infrastructure.config.httpexceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@Log4j2
public class GlobalExceptionRestHandlerConfiguration {
    @ExceptionHandler(HttpException.class)
    public ResponseEntity<ApiErrorDto> handleBaseException(HttpException e) {
        log.error("http exception occurred: {}", e.getMessage());
        String message = e.getMessage();
        HttpStatus statusCode = e.getStatusCode();

        ApiErrorDto response = ApiErrorDto.builder()
                .messages(List.of(message))
                .status(statusCode)
                .build();

        return ResponseEntity.status(statusCode.value()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        log.error("Validation error occurred: {}", ex.getMessage());
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.add(error.getField() + " " + error.getDefaultMessage()));
        ApiErrorDto response = ApiErrorDto.builder()
                .messages(errors)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ApiErrorDto> handleGeneralExceptions(Exception ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        ApiErrorDto.ApiErrorDtoBuilder messages = ApiErrorDto.builder()
                .messages(getErrorsInternalServer(errors));
        ApiErrorDto response = messages
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ApiErrorDto> handleRuntimeExceptions(RuntimeException ex) {
        List<String> errors = Collections.singletonList(ex.getMessage());
        List<String> messages = getErrorsInternalServer(errors);
        ApiErrorDto response = ApiErrorDto.builder()
                .messages(messages)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(response);
    }


    private List<String> getErrorsInternalServer(List<String> errors) {
        log.error(errors.toString());
        List<String> errorsMessage = new ArrayList<>();
        errorsMessage.add("Something went wrong, please try later");
        return errorsMessage;
    }
}
