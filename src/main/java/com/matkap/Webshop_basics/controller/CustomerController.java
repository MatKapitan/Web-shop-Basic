package com.matkap.Webshop_basics.controller;

import com.matkap.Webshop_basics.dto.CustomerDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<Customer>> getAllCustomers(@PageableDefault(value = 5, page = 0) Pageable page){
        return new ResponseEntity<>(customerService.getAllOCustomers(page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createCustomer(@Valid @RequestBody CustomerDto customerDto){
        customerService.createCustomer(customerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteCustomerByID(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{customer_id}")
    public ResponseEntity<HttpStatus> updateCustomer(@Valid @RequestBody CustomerDto customerDto, @PathVariable Long customer_id){
        customerService.updateCustomer(customerDto, customer_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
