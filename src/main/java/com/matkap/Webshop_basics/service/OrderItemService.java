package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.dto.QuantityRequest;
import com.matkap.Webshop_basics.entity.OrderItem;

import java.util.List;

public interface OrderItemService {

    void createOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id);
    void deleteOrderItem(Long id);

    void updateOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id);


}
