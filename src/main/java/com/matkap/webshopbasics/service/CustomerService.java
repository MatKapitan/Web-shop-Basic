package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.CustomerDto;
import com.matkap.webshopbasics.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerDto getCustomerById(Long id);
    Page<Customer> getAllOCustomers(Pageable page);
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(CustomerDto customerDto , Long id);
    void deleteCustomer(Long id);
}
