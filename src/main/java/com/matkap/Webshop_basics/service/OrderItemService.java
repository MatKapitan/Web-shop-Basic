package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.dto.QuantityRequest;

public interface OrderItemService {

    ProductDto getOrderItemById(Long id, Long productId);
    void createOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id);
    void deleteOrderItem(Long id);

    void updateOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id);


}
