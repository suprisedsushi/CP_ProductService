package com.example.productservice.services;

import com.example.productservice.dtos.FakeStoreProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.stereotype.Service;
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

        if(fakeStoreProductDto == null) {
            return  null;
        }

        //Convert FakeStoreProductDto object  into Product object
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
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
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject("https://fakestoreapi.com/products/",
                FakeStoreProductDto[].class);

        if(fakeStoreProductDtos == null) {
            return new ArrayList<>();
        }

        //Convert FakeStoreProductDto objects into List<Product> object
        return convertFakeStoreProductDtoToProducts(fakeStoreProductDtos);
    }
}
