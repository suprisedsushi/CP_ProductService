package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<Product> getProductById(Long id) throws ProductNotFoundException;

    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<List<Product>> getInCategory(String name);

    ResponseEntity<Product> createProduct(Product product) throws ProductNotFoundException;

    ResponseEntity<Product> replaceProduct(Long id, Product product) throws ProductNotFoundException;

    ResponseEntity<Product> deleteProduct(Long id) throws ProductNotFoundException;
}
