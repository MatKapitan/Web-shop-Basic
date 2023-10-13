package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerDto getCustomerById(Long id);
    Page<CustomerDto> getAllOCustomers(Pageable page);
    Long createCustomer(CustomerDto customerDto);
    void updateCustomer(CustomerDto customerDto , Long id);
    void deleteCustomer(Long id);
}
