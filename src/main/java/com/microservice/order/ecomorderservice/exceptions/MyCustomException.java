package com.microservice.order.ecomorderservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class MyCustomException extends RuntimeException{

    private final HttpStatusCode statusCode;
    private final HttpHeaders headers;

    public MyCustomException(HttpStatusCode statusCode, HttpHeaders headers) {
        super("HTTP Error: " + statusCode);
        this.statusCode = statusCode;
        this.headers = headers;
    }
}
