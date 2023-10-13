package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.entity.Product;
import com.matkap.Webshop_basics.exception.notFound.ProductNotFoundException;
import com.matkap.Webshop_basics.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;



    @Test
    void getProduct_elementExist_shouldReturnProduct() {
        Product product = new Product("Apple", BigDecimal.valueOf(1.23), "Red apple", true);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDto result = productService.getProduct(1L);

        assertEquals("Apple", result.getName());
        assertEquals(BigDecimal.valueOf(1.23), result.getPriceEur());
        assertEquals("Red apple", result.getDescription());
        verify(productRepository, only()).findById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void getProduct_nonExistentElement_shouldThrowException(){
        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> this.productService.getProduct(1L))
                .withMessage("The Product with id '1' does not exist in our records");
    }

    @Test
    void getProduct_nullElement_shouldThrowException(){
        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> this.productService.getProduct(null))
                .withMessage("The Product with id 'null' does not exist in our records");
    }





    @Test
    void getProducts_elementsExist_shouldReturnAllProducts() {
        PageImpl page = new PageImpl(List.of(
                new Product("Apple", BigDecimal.valueOf(1.23),"Red apple",true),
                new Product("Pear", BigDecimal.valueOf(1.55),"Purple small pear",true)
                ));
        when(productRepository.findAll(PageRequest.of(1 , 10))).thenReturn(page);
        Page<ProductDto> result = productService.getProducts(PageRequest.of(1,10));

        assertEquals(2, result.getSize());
        assertEquals(2, result.getContent().size());

        assertEquals("Apple", result.getContent().get(0).getName());
        assertEquals(BigDecimal.valueOf(1.23), result.getContent().get(0).getPriceEur());
        assertEquals("Red apple", result.getContent().get(0).getDescription());
        assertEquals("Pear", result.getContent().get(1).getName());
        assertEquals(BigDecimal.valueOf(1.55), result.getContent().get(1).getPriceEur());
        assertEquals("Purple small pear", result.getContent().get(1).getDescription());
        verify(productRepository, only()).findAll(PageRequest.of(1,10));
        verifyNoMoreInteractions(productRepository);
    }



    @Test
    void deleteProduct_elementsExist_shouldDeleteEntity() {
        doNothing().when(productRepository).deleteById(anyLong());

        productService.deleteProduct(anyLong());
        verify(productRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(productRepository);
    }
}