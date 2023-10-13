package com.matkap.Webshop_basics.controller;


import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

   private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProduct(id), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDto>> getProducts(@PageableDefault(value = 5, page = 0) Pageable page){
        return new ResponseEntity<>(productService.getProducts(page),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@Valid @RequestBody ProductDto productDto){
        Long new_id = productService.createProduct(productDto);
        return new ResponseEntity<>(String.format("new product with id '%s' has been created",new_id),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{product_id}")
    public ResponseEntity<HttpStatus> updateProduct(@Valid @RequestBody ProductDto productDto,
                                                  @PathVariable Long product_id) {
        productService.updateProduct(productDto, product_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
