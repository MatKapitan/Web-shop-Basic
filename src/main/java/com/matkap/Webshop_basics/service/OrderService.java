package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {

    OrderDto getOrderById(Long id);
    Page<Order> getAllOrders(Pageable page);
    void createOrder(Long customer_id);
    void deleteOrder(Long id);
    OrderDto finaliseOrder(Long id);




}
