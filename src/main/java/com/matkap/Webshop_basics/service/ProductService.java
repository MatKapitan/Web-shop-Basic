package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductDto getProduct(Long id);
    Page<ProductDto> getProducts(Pageable page);
    void createProduct(ProductDto productDto);
    void deleteProduct(Long id);
    Page<ProductDto> getProductsByOrderId(Long orderId, Pageable page);
    void updateProduct(ProductDto productDto, Long product_id);
}
