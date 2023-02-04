package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

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