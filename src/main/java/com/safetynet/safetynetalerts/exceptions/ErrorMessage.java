package com.safetynet.safetynetalerts.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private HttpStatus statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
