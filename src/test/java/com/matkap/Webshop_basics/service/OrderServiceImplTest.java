package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.CustomerDto;
import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.StatusEnum;
import com.matkap.Webshop_basics.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderServiceImpl orderService;





    @Test
    public void getOrderById_elementsExist_shouldFindAndReturnOneCustomer(){
        Order order = new Order(new Customer(), StatusEnum.DRAFT);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDto result = orderService.getOrderById(1L);

        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }





}