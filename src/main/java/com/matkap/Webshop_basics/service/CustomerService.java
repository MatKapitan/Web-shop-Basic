package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.CustomerDto;
import com.matkap.Webshop_basics.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {

    CustomerDto getCustomerById(Long id);
    Page<Customer> getAllOCustomers(Pageable page);
    void createCustomer(CustomerDto customerDto);
    void updateCustomer(CustomerDto customerDto , Long id);
    void deleteCustomer(Long id);
}
