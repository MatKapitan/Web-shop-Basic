package com.matkap.webshopbasics.controller;

import com.matkap.webshopbasics.dto.CustomerDto;
import com.matkap.webshopbasics.entity.Customer;
import com.matkap.webshopbasics.service.CustomerService;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDto> getById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(customerService.getCustomerById(id));
  }

  @GetMapping
  public ResponseEntity<Page<Customer>> getAll(
      @PageableDefault(value = 5, page = 0) Pageable page) {
    return ResponseEntity.ok(customerService.getAllOCustomers(page));
  }

  @PostMapping
  public ResponseEntity<HttpStatus> create(@Valid @RequestBody CustomerDto customerDto) {
    CustomerDto customer = customerService.createCustomer(customerDto);
    URI self = UriComponentsBuilder.newInstance().path("/customer/{id}")
        .build(customer.getId());
    return ResponseEntity.created(self).build();
  }

  @DeleteMapping("{id}")
  public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("{id}")
  public ResponseEntity<HttpStatus> updateCustomer(@PathVariable("id") Long id,
      @Valid @RequestBody CustomerDto customerDto) {
    customerService.updateCustomer(customerDto, id);
    return ResponseEntity.noContent().build();
  }


}
