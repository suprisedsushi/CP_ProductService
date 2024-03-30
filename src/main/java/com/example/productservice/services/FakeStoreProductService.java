package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {
    private final RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {

        //Call FakeStore API to get the Product with given id
        RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET,
                URI.create("https://fakestoreapi.com/products"));

        return getProductListResponseEntity(request);
    }

    @Override
    public ResponseEntity<Product> getProductById(Long id) {

        RequestEntity<FakeStoreProductDto> entity = new RequestEntity<>(null, HttpMethod.GET,
                URI.create("https://fakestoreapi.com/products/" + id));

        return getProductResponseEntity(entity);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {

        ResponseEntity<String[]> response = restTemplate.getForEntity("https://fakestoreapi.com/products/categories",
                String[].class);

        if(response.getBody() == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        //Convert String into Category object
        List<Category> categories = convertFakeStoreProductDtoToCategories(response.getBody());

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getInCategory(String name) {

        //encode characters in name before creating URI
        String encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8);

        //encode method convert space to '+' hence replacing it with %20
        encodedName = encodedName.replaceAll("\\+", "%20");

        RequestEntity<Object> request = new RequestEntity<>(HttpMethod.GET,
                URI.create("https://fakestoreapi.com/products/category/" + encodedName));

        return getProductListResponseEntity(request);
    }

    @Override
    public ResponseEntity<Product> createProduct(Product product) {

        try {

            FakeStoreProductDto dto = new FakeStoreProductDto();
            dto.setTitle(product.getTitle());
            dto.setPrice(product.getPrice());
            dto.setDescription(product.getDescription());
            dto.setImage(product.getImage());
            dto.setCategory(product.getCategory().getDescription());

            RequestEntity<FakeStoreProductDto> entity = new RequestEntity<>(dto, HttpMethod.POST,
                    URI.create("https://fakestoreapi.com/products"));

            return getProductResponseEntity(entity);

        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id, Product product) {

        try {

            FakeStoreProductDto dto = new FakeStoreProductDto();
            dto.setTitle(product.getTitle());
            dto.setImage(product.getImage());
            dto.setDescription(product.getDescription());

            RequestEntity<FakeStoreProductDto> entity = new RequestEntity<>(dto, HttpMethod.PUT,
                    URI.create("https://fakestoreapi.com/products/" + id));

            return getProductResponseEntity(entity);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) {

        RequestEntity<FakeStoreProductDto> entity = new RequestEntity<>(null, HttpMethod.DELETE,
                URI.create("https://fakestoreapi.com/products/" + id));

        return getProductResponseEntity(entity);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto dto) {

        Product product = new Product();
        try {

            product.setId(dto.getId());
            product.setTitle(dto.getTitle());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setImage(dto.getImage());

            Category category = new Category();
            category.setDescription(dto.getCategory());

            product.setCategory(category);

        } catch (Exception ex) {
            return null;
        }

        return product;
    }

    private List<Product> convertFakeStoreProductDtoToProducts(FakeStoreProductDto[] dtos) {

        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto dto : dtos) {
            Product product = convertFakeStoreProductDtoToProduct(dto);

            if(product == null) {
                return null;
            }

            products.add(product);
        }
        return products;
    }

    private List<Category> convertFakeStoreProductDtoToCategories(String[] names) {

        List<Category> categories = new ArrayList<>();
        for (String name : names) {
            Category category = new Category();
            category.setDescription(name);

            categories.add(category);
        }
        return categories;
    }

    private ResponseEntity<Product> getProductResponseEntity(RequestEntity<FakeStoreProductDto> entity) {

        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(entity, FakeStoreProductDto.class);

        if(response.getBody() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product outProduct = convertFakeStoreProductDtoToProduct(response.getBody());

        if(outProduct == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(outProduct, HttpStatus.OK);
    }

    private ResponseEntity<List<Product>> getProductListResponseEntity(RequestEntity<Object> entity) {

        ResponseEntity<FakeStoreProductDto[]> response = restTemplate.exchange(entity, FakeStoreProductDto[].class);

        if(response.getBody() == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //Convert FakeStoreProductDto array object into List of Product object
        List<Product> products = convertFakeStoreProductDtoToProducts(response.getBody());

        if(products == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
