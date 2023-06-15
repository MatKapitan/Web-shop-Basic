package com.matkap.webshopbasics.controller;

import com.matkap.webshopbasics.dto.ProductDto;
import com.matkap.webshopbasics.dto.QuantityRequest;
import com.matkap.webshopbasics.service.OrderItemService;
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
@RequestMapping("/order/{id}/product")
public class ProductForOrderController {

  private final ProductService productService;
  private final OrderItemService orderItemService;

  public ProductForOrderController(ProductService productService,
      OrderItemService orderItemService) {
    this.productService = productService;
    this.orderItemService = orderItemService;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductDto> getOrderItemById(@PathVariable("id") Long id,
      @PathVariable(name = "productId") Long productId) {
    return ResponseEntity.ok(orderItemService.getOrderItemById(id, productId));
  }

  @GetMapping
  public ResponseEntity<Page<ProductDto>> getAllOrderItems(@PathVariable("id") Long id,
      @PageableDefault(value = 5, page = 0) Pageable page) {
    return ResponseEntity.ok(this.productService.getProductsInOrderById(id, page));
  }

  @PostMapping("/{productId}")
  public ResponseEntity<Void> createOrderItem(
      @Valid @RequestBody QuantityRequest quantityRequest,
      @PathVariable(name = "id") Long id,
      @PathVariable(name = "productId") Long productId,
      UriComponentsBuilder uriComponentsBuilder) {
    orderItemService.addProductOnOrder(quantityRequest, id, productId);
    URI uri = uriComponentsBuilder.path("/order/{id}/product/{productId}")
        .pathSegment(id.toString(), productId.toString()).build().toUri();
    return ResponseEntity.created(uri).build();
  }

  @DeleteMapping("{productId}")
  public ResponseEntity<Void> deleteOrderItemById(
      @PathVariable(name = "id") Long id,
      @PathVariable(name = "productId") Long productId) {
    orderItemService.deleteOrderItem(id, productId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{productId}")
  public ResponseEntity<Void> updateOrderItem(
      @Valid @RequestBody QuantityRequest quantityRequest,
      @PathVariable(name = "id") Long id,
      @PathVariable(name = "productId") Long productId) {
    orderItemService.updateOrderItem(quantityRequest, id, productId);
    return ResponseEntity.noContent().build();
  }
}
