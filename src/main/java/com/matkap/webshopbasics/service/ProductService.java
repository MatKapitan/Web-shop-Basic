package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    ProductDto getProduct(Long id);
    Page<ProductDto> getProducts(Pageable page);
    void createProduct(ProductDto productDto);
    void deleteProduct(Long id);
    Page<ProductDto> getProductsInOrderById(Long orderId, Pageable page);
    void updateProduct(ProductDto productDto, Long product_id);
}
