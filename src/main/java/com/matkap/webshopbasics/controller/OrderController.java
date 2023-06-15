package com.matkap.webshopbasics.controller;

import com.matkap.webshopbasics.dto.OrderDto;
import com.matkap.webshopbasics.service.OrderService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  OrderService orderService;

  @GetMapping("/{id}")
  public ResponseEntity<OrderDto> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(orderService.getById(id));
  }

  @GetMapping
  public ResponseEntity<Page<OrderDto>> getAll(
      @PageableDefault(value = 5, page = 0) Pageable page) {
    return ResponseEntity.ok(orderService.getAll(page));
  }

  @PostMapping("/{id}")
  public ResponseEntity<Void> create(@PathVariable("id") Long id,
      UriComponentsBuilder uriComponentsBuilder) {
    OrderDto order = orderService.create(id);
    URI self = uriComponentsBuilder.path("/order/{id}")
        .pathSegment(order.getId().toString())
        .build().toUri();
    return ResponseEntity.created(self).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
    orderService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> calculateFinalPrice(@PathVariable("id") Long id) {
    orderService.finalise(id);
    return ResponseEntity.noContent().build();

  }


}
