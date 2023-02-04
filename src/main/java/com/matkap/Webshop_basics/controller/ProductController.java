package com.matkap.Webshop_basics.controller;


import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.entity.Product;
import com.matkap.Webshop_basics.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDto>> getProducts(@PageableDefault(value = 5, page = 0) Pageable page){
        return new ResponseEntity<>(productService.getProducts(page),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> saveProduct(@Valid @RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{product_id}")
    public ResponseEntity<HttpStatus> saveProduct(@Valid @RequestBody ProductDto productDto,
                                                  @PathVariable Long product_id) {
        productService.updateProduct(productDto, product_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
