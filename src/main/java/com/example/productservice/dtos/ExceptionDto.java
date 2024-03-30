package com.example.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionDto {
    String error;

    public ExceptionDto(String error) {
        this.error = error;
    }
}
