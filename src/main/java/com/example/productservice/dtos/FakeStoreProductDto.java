package com.example.productservice.dtos;

import lombok.Getter;

@Getter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
}
