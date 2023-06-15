package com.matkap.webshopbasics.controller;


import com.matkap.webshopbasics.dto.ProductDto;
import com.matkap.webshopbasics.service.ProductService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/product")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductDto> getProduct(@PathVariable("id") Long id) {
    return ResponseEntity.ok(productService.getProduct(id));
  }

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getProducts(
      @PageableDefault(value = 5, page = 0) Pageable page) {
    return ResponseEntity.ok(productService.getProducts(page));
  }

  @PostMapping
  public ResponseEntity<Void> saveProduct(@Valid @RequestBody ProductDto productDto,
      UriComponentsBuilder uriComponentsBuilder) {
    productService.createProduct(productDto);
    URI uri = uriComponentsBuilder.path("/product/{id}").pathSegment()
        .build().toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> saveProduct(@Valid @RequestBody ProductDto productDto,
      @PathVariable("id") Long id) {
    productService.updateProduct(productDto, id);
    return ResponseEntity.noContent().build();
  }

}
