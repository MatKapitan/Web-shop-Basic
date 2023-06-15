package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.ProductDto;
import com.matkap.webshopbasics.dto.QuantityRequest;

public interface OrderItemService {

    void addProductOnOrder(QuantityRequest quantityRequest, Long orderId, Long productId);
    void deleteOrderItem(Long id, Long productId);

    void updateOrderItem(QuantityRequest quantityRequest, Long orderId, Long productId);


    ProductDto getOrderItemById(Long id, Long productId);
}
