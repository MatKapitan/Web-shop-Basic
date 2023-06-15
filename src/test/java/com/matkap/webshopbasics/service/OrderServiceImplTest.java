package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.OrderDto;
import com.matkap.webshopbasics.entity.Customer;
import com.matkap.webshopbasics.entity.Order;
import com.matkap.webshopbasics.entity.StatusEnum;
import com.matkap.webshopbasics.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

        OrderDto result = orderService.getById(1L);

        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }





}