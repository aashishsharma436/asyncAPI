package com.practice.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private int statusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private ErrorResponse() {
        timestamp = LocalDateTime.now();
    }

    ErrorResponse(HttpStatus status) {
        this();
        this.status = status;
        this.statusCode = status.value();
    }

    ErrorResponse(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.statusCode = status.value();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    ErrorResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
        this.debugMessage = ex.getLocalizedMessage();
    }
}
