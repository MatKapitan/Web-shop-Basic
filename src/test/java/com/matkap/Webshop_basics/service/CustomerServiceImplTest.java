package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.CustomerDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.exception.notFound.CustomerNotFoundException;
import com.matkap.Webshop_basics.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 public class CustomerServiceImplTest {

//    UnitOfWork_StateUnderTest_ExpectedBehavior
    //getCustomerById - normal, null, exception
    //getAllCustomers - normal
    //updateCustomer - exception
    //deleteCustomer - normal


    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Test
    public void getAllOCustomers_elementsExist_shouldReturnAllCustomer() {
        PageImpl page = new PageImpl(List.of(
                new Customer("Matija", "Kapitan", "matija.kapitan1@gmail.com"),
                new Customer("Petar", "Bla", "1dvsdv@EAfw.com")
        ));
        when(customerRepository.findAll(PageRequest.of(1 , 10))).thenReturn(page);

        Page<CustomerDto> result = customerService.getAllOCustomers(PageRequest.of(1,10));

        assertEquals(2, result.getSize());
        assertEquals(2, result.getContent().size());

        assertEquals("Matija", result.getContent().get(0).getFirst_name());
        assertEquals("Kapitan", result.getContent().get(0).getLast_name());
        assertEquals("matija.kapitan1@gmail.com", result.getContent().get(0).getEmail());
        assertEquals("Petar", result.getContent().get(1).getFirst_name());
        assertEquals("Bla", result.getContent().get(1).getLast_name());
        assertEquals("1dvsdv@EAfw.com", result.getContent().get(1).getEmail());
        verify(customerRepository, only()).findAll(PageRequest.of(1,10));
        verifyNoMoreInteractions(customerRepository);
        //verifyNoInteractions(customerRepository);
    }


    @Test
    public void getCustomerById_elementsExist_shouldFindAndReturnOneCustomer(){
        Customer customer = new Customer("Matija", "Kapitan", "matija.kapitan1@gmail.com");
        CustomerDto customerDto = customerService.customerToDto(customer);
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        CustomerDto result = customerService.getCustomerById(0L);

        assertEquals("Matija", result.getFirst_name());
        assertEquals("Kapitan", result.getLast_name());
        assertEquals("matija.kapitan1@gmail.com", result.getEmail());
        verify(customerRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }

//    @Test
//    public void getCustomerById_nullElement_shouldThrowException(){
//        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class,
//                () -> this.customerService.getCustomerById(any()));
//        assertThat(exception).extracting(Throwable::getMessage).isEqualTo("The Customer with id 'null' does not exist in our records");
//        assertThat(exception.getMessage()).isEqualTo("The Customer with id 'null' does not exist in our records");
//    }

    @Test
    public void getCustomerById_nonExistentElement_shouldThrowException(){
        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> this.customerService.getCustomerById(5L))
                .withMessage("The Customer with id '5' does not exist in our records");
    }

    @Test
    public void getCustomerById_nullElement_shouldThrowException(){
        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> this.customerService.getCustomerById(null))
                .withMessage("The Customer with id 'null' does not exist in our records");
    }


    @Test
    public void updateCustomer_nonExistentCustomer_shouldThrowException(){
        assertThatExceptionOfType(CustomerNotFoundException.class)
                .isThrownBy(() -> this.customerService.updateCustomer(new CustomerDto(),5L))
                .withMessage("The Customer with id '5' does not exist in our records");
    }

    @Test
    public void deleteCustomer_elementsExist_shouldDeleteEntity() {
        doNothing().when(customerRepository).deleteById(anyLong());

        customerService.deleteCustomer(anyLong());
        verify(customerRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }




}