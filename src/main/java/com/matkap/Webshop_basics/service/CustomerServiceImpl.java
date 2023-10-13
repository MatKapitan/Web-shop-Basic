package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.CustomerDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.exception.notFound.CustomerNotFoundException;
import com.matkap.Webshop_basics.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return customerToDto(customer);
    }

    @Override
    public Page<CustomerDto> getAllOCustomers(Pageable page) {
        Page<Customer> customers = customerRepository.findAll(page);
        return customers.map(CustomerDto::from);


        //return customerRepository.findAll(PageRequest.of(page.getPageNumber(),page.getPageSize()));

    }

    @Override
    public Long createCustomer(CustomerDto customerDto) {
        Customer customer = customerDtoToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer.getId();
    }

    @Override
    public void updateCustomer(CustomerDto customerDto, Long id) {
        Customer customerToUpdate = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        customerToUpdate.setFirst_name(customerDto.getFirst_name());
        customerToUpdate.setLast_name(customerDto.getLast_name());
        customerToUpdate.setEmail(customerDto.getEmail());
        customerRepository.save(customerToUpdate);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

//--------------------------------------------------------------------------------------
    // Entity to Dto
    // Dto to entity

    public CustomerDto customerToDto(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setEmail(customer.getEmail());
        customerDto.setFirst_name(customer.getFirst_name());
        customerDto.setLast_name(customer.getLast_name());
        return customerDto;
    }

    public Customer customerDtoToEntity(CustomerDto customerDto){
        Customer customer = new Customer();
        customer.setId(customerDto.getId());
        customer.setEmail(customerDto.getEmail());
        customer.setFirst_name(customerDto.getFirst_name());
        customer.setLast_name(customerDto.getLast_name());
        return customer;
    }
}
