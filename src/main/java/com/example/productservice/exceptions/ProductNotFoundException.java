package com.example.productservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;

@Getter
@Setter
public class ProductNotFoundException extends Exception {

    private Long id;

    private HttpMethod method;

    public ProductNotFoundException(Long id, HttpMethod method, String message) {
        super(message);
        this.id = id;
        this.method = method;
    }
}
