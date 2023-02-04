package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.dto.QuantityRequest;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.OrderItem;
import com.matkap.Webshop_basics.entity.Product;
import com.matkap.Webshop_basics.entity.StatusEnum;
import com.matkap.Webshop_basics.exception.OrderFinalisedException;
import com.matkap.Webshop_basics.exception.notFound.OrderItemNotFoundException;
import com.matkap.Webshop_basics.exception.notFound.OrderNotFoundException;
import com.matkap.Webshop_basics.exception.ProductAlreadyAddedException;
import com.matkap.Webshop_basics.exception.notFound.ProductNotFoundException;
import com.matkap.Webshop_basics.repository.OrderItemRepository;
import com.matkap.Webshop_basics.repository.OrderRepository;
import com.matkap.Webshop_basics.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;



    @Override
    public void createOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id) {
        Order order = orderRepository.findById(order_id).orElseThrow(()-> new OrderNotFoundException(order_id));
        if (order.getStatusEnum().equals(StatusEnum.SUBMITTED)) throw new OrderFinalisedException(order_id);
        Product product = productRepository.findByIdAndIsAvailable(product_id, true).orElseThrow(()-> new ProductNotFoundException(product_id));
        //trowIfProductContain(product_id, order);
        if (orderItemRepository.existsByProduct_IdAndOrder_Id(product_id, order_id)) throw new ProductAlreadyAddedException();
        OrderItem orderItem = new OrderItem(order, product, quantityRequest.getQuantity());
        orderItemRepository.save(orderItem);
    }

//    private void trowIfProductContain(Long product_id, Order order) {
//        order.getOrderItemList().forEach((x)-> {
//        if (x.getProduct().getId().equals(product_id)) throw new ProductHasBeenAlreadyAddedException();});
//        List<Long> productIds = order.getOrderItemList().stream().map(x -> x.getProduct()).map(x -> x.getId()).collect(Collectors.toList());
//        if (productIds.contains(product_id)) throw new ProductHasBeenAlreadyAddedException();
//
//        boolean isProductContain = order.getOrderItemList().stream().anyMatch(x -> x.getProduct().getId().equals(product_id));
//        if (isProductContain) throw new ProductAlreadyAddedException();
//    }


    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    @Override
    public void updateOrderItem(QuantityRequest quantityRequest, Long order_id, Long product_id) {
        OrderItem orderItemToUpdate = orderItemRepository.findByProduct_IdAndOrder_Id(product_id, order_id).orElseThrow(()-> new OrderItemNotFoundException(order_id, product_id));
        orderItemToUpdate.setQuantity(quantityRequest.getQuantity());
        orderItemRepository.save(orderItemToUpdate);



    }
}
