package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Component
//@Repository
@Service
public class FakeStoreProductService implements ProductService{
    private final RestTemplate restTemplate;

    FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Product getProductById(Long id) {
        //Call FakeStore API to get the Product with given id
        //1st Parameter -> url
        //2nd Parameter -> return type object class
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);

        //Convert FakeStoreProductDto object  into Product object
        return (fakeStoreProductDto == null) ? null : convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());

        Category category = new Category();
        category.setDescription(dto.getCategory());

        product.setCategory(category);

        return product;
    }

    private List<Product> convertFakeStoreProductDtoToProducts(FakeStoreProductDto[] dtos) {
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto dto : dtos) {
            products.add(convertFakeStoreProductDtoToProduct(dto));
        }
        return products;
    }

    @Override
    public List<Product> getAllProducts() {
        //Call FakeStore API to get all the Products
        //1st Parameter -> url
        //2nd Parameter -> return type object class
        FakeStoreProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products/",
                FakeStoreProductDto[].class);

        //Convert FakeStoreProductDto objects into List<Product> object
        return (dtos == null) ? null : convertFakeStoreProductDtoToProducts(dtos);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(product.getTitle());
        dto.setImage(product.getImage());
        dto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(dto, FakeStoreProductDto.class);

        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,
                        restTemplate.getMessageConverters());

        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id,
                HttpMethod.PUT, requestCallback, responseExtractor);

        return (response == null) ? null : convertFakeStoreProductDtoToProduct(response);
    }

    @Override
    public List<Product> getInCategory(String name) {
        //Call FakeStore API to get the Product with given id
        //1st Parameter -> url
        //2nd Parameter -> return type object class

        FakeStoreProductDto[] dtos = restTemplate.getForObject(
                "https://fakestoreapi.com/products/category/" + name,
                FakeStoreProductDto[].class);

        //Convert FakeStoreProductDto object  into Product object
        return (dtos == null) ? null : convertFakeStoreProductDtoToProducts(dtos);
    }

    private Category convertFakeStoreProductDtoToCategory(String name) {
        Category category = new Category();
        category.setDescription(name);
        return category;
    }

    private List<Category> convertFakeStoreProductDtoToCategories(String[] names) {
        List<Category> categories = new ArrayList<>();
        for (String name : names) {
            categories.add(convertFakeStoreProductDtoToCategory(name));
        }
        return categories;
    }

    @Override
    public List<Category> getAllCategories() {
        //Call FakeStore API to get all the Products
        //1st Parameter -> url
        //2nd Parameter -> return type object class
        String[] categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories",
                String[].class);

        //Convert FakeStoreProductDto object  into Product object
        return (categories == null) ? null : convertFakeStoreProductDtoToCategories(categories);
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(product.getTitle());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setCategory(product.getCategory().getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(dto, FakeStoreProductDto.class);

        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class,
                        restTemplate.getMessageConverters());

        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products",
                HttpMethod.POST, requestCallback, responseExtractor);

        return (response == null) ? null : convertFakeStoreProductDtoToProduct(response);
    }

    @Override
    public Product deleteProduct(Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType((MediaType.APPLICATION_JSON));

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
                "https://fakestoreapi.com/products/" + id, HttpMethod.DELETE, requestEntity,
                FakeStoreProductDto.class);

        return convertFakeStoreProductDtoToProduct(responseEntity.getBody());
    }
}
