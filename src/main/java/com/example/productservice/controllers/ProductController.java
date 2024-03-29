package com.example.productservice.controllers;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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

    //localhost:8080/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {

        Product product =  productService.getProductById(id);

        if(product == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //localhost:8080/products/
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if(products == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //createProduct
    //deleteProduct
    //updateProduct -> Partial Update (PATCH)
    //replaceProduct -> Replace (PUT)

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id, @RequestBody Product inProduct) {
        Product outProduct = productService.replaceProduct(id, inProduct);

        if(outProduct == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(outProduct, HttpStatus.OK);
    }

    //localhost:8080/products/categories/{name}
    @GetMapping("/categories/{name}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable("name") String name) {
        List<Product> products =  productService.getInCategory(name);

        if(products == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //localhost:8080/products/categories
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = productService.getAllCategories();

        if(categories == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //localhost:8080/products/
    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product inProduct) {
        Product outProduct = productService.createProduct(inProduct);

        if(outProduct == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(outProduct, HttpStatus.OK);
    }

    //localhost:8080/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id) {
        Product outProduct = productService.deleteProduct(id);

        if(outProduct == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(outProduct, HttpStatus.OK);
    }
}
