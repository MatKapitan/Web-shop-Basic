package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;



    @Test
    void getProduct() {
    }

    @Test
    void getProducts() {
    }

    @Test
    void saveProduct() {
    }

    @Test
    void deleteProduct() {
    }
}