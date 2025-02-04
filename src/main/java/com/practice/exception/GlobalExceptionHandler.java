package com.practice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.server.EntityResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException ex) {
        List<String> errors = ex.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)  // Extract only validation messages
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, String.join("; ", errors),ex);
        return Mono.just(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ErrorResponse> handleInternalServerException(Exception ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR);
        apiError.setMessage(ex.getMessage());
        buildResponseEntity(apiError);
        return Mono.just(apiError);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected Mono<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        ErrorResponse apiError = new ErrorResponse(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        buildResponseEntity(apiError);
        return Mono.just(apiError);
    }

    private Mono<EntityResponse<ErrorResponse>> buildResponseEntity(ErrorResponse apiError) {
        log.error("GlobalExceptionHandler::buildResponseEntity exception {}", apiError.getMessage());
        return EntityResponse.fromObject(apiError).build();
    }
}
