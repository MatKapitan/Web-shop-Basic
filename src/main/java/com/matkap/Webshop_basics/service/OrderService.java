package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderDto getOrderById(Long id);
    Page<OrderDto> getAllOrders(Pageable page);
    Long createOrder(Long customer_id);
    void deleteOrder(Long id);
    OrderDto finaliseOrder(Long id);




}
