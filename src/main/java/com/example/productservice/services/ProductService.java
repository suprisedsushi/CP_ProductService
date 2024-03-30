package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<Product> getProductById(Long id);

    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<List<Product>> getInCategory(String name);

    ResponseEntity<Product> createProduct(Product product);

    ResponseEntity<Product> replaceProduct(Long id, Product product);

    ResponseEntity<Product> deleteProduct(Long id);
}
