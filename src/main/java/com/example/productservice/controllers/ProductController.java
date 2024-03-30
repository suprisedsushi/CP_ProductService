package com.example.productservice.controllers;

import com.example.productservice.dtos.ProductNotFoundExceptionDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//localhost:8080/products
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    //localhost:8080/products/
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    //localhost:8080/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    //localhost:8080/products/categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return productService.getAllCategories();
    }

    //localhost:8080/products/categories/{name}
    @GetMapping("/categories/{name}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable("name") String name) {
        return productService.getInCategory(name);
    }

    //localhost:8080/products/
    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product inProduct) throws ProductNotFoundException {
        return productService.createProduct(inProduct);
    }

    //localhost:8080/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product inProduct) throws ProductNotFoundException {
        return productService.replaceProduct(id, inProduct);
    }

    //localhost:8080/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }

    //Controller specific exception handler. First priority given to this. Then Global exception handler
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleProductNotFoundExceptionHandler(ProductNotFoundException ex) {

        ProductNotFoundExceptionDto dto = new ProductNotFoundExceptionDto();
        dto.setId(ex.getId());

        if(ex.getMethod() == HttpMethod.GET) {
            dto.setError("Failed to get product with id " + dto.getId());
        } else if(ex.getMethod() == HttpMethod.POST) {
            dto.setError("Failed to create product");
        } else if(ex.getMethod() == HttpMethod.PUT) {
            dto.setError("Failed to replace product with id " + dto.getId());
        } else if(ex.getMethod() == HttpMethod.DELETE) {
            dto.setError("Failed to delete product with id " + dto.getId());
        } else {
            dto.setError("Error with product id " + dto.getId());
        }

        return new ResponseEntity<>(dto, HttpStatus.NOT_FOUND);
    }
}
