package com.example.productservice.services;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);

    List<Product> getAllProducts();

    Product replaceProduct(Long id, Product product);

    List<Product> getInCategory(String name);

    List<Category> getAllCategories();

    Product createProduct(Product product);

    Product deleteProduct(Long id);
}
