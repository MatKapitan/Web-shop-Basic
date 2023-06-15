package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.ProductDto;
import com.matkap.webshopbasics.dto.QuantityRequest;
import com.matkap.webshopbasics.entity.Order;
import com.matkap.webshopbasics.entity.OrderItem;
import com.matkap.webshopbasics.entity.Product;
import com.matkap.webshopbasics.entity.StatusEnum;
import com.matkap.webshopbasics.exception.OrderFinalisedException;
import com.matkap.webshopbasics.exception.ProductAlreadyAddedException;
import com.matkap.webshopbasics.exception.notFound.OrderItemNotFoundException;
import com.matkap.webshopbasics.exception.notFound.OrderNotFoundException;
import com.matkap.webshopbasics.exception.notFound.ProductNotFoundException;
import com.matkap.webshopbasics.repository.OrderRepository;
import com.matkap.webshopbasics.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {


  private final OrderRepository orderRepository;

  private final ProductRepository productRepository;

  public OrderItemServiceImpl(
      OrderRepository orderRepository, ProductRepository productRepository) {
    this.orderRepository = orderRepository;
    this.productRepository = productRepository;
  }


  @Override
  public void addProductOnOrder(QuantityRequest quantityRequest, Long orderId, Long productId) {
    Order order = getOrderOrThorw(orderId);
    throwIfOrderAlreadySubmitted(orderId, order);
    Product product = getProductsOrThrow(productId);
    throwIfProductAlreadyPresent(orderId, productId);
    OrderItem orderItem = new OrderItem(order, product, quantityRequest.getQuantity());
    order.addProduct(orderItem);
    orderRepository.save(order);
  }

  @Override
  public void deleteOrderItem(Long id, Long productId) {
    Order order = orderRepository.findById(id).orElseThrow();
    order.getOrderItemList().removeIf(x -> x.getId().equals(productId));
    orderRepository.save(order);
  }

  @Override
  public void updateOrderItem(QuantityRequest quantityRequest, Long orderId, Long productId) {
    OrderItem orderItemToUpdate = orderItemRepository.findByProduct_IdAndOrder_Id(productId,
        orderId).orElseThrow(() -> new OrderItemNotFoundException(orderId,
        productId));
    orderItemToUpdate.setQuantity(quantityRequest.getQuantity());
    orderItemRepository.save(orderItemToUpdate);

  }

  @Override
  public ProductDto getOrderItemById(Long id, Long productId) {
    return this.productRepository.findByIdAndOrderId(productId, id)
        .map(ProductServiceImpl::productToDto).orElseThrow();
  }

  private void throwIfProductAlreadyPresent(Long orderId, Long productId) {
    if (orderItemRepository.existsByProduct_IdAndOrder_Id(productId, orderId)) {
      throw new ProductAlreadyAddedException();
    }
  }

  private Product getProductsOrThrow(Long productId) {
    return productRepository.findByIdAndAvailable(productId, true)
        .orElseThrow(() -> new ProductNotFoundException(
            productId));
  }

  private static void throwIfOrderAlreadySubmitted(Long orderId, Order order) {
    if (order.getStatusEnum().equals(StatusEnum.SUBMITTED)) {
      throw new OrderFinalisedException(
          orderId);
    }
  }

  private Order getOrderOrThorw(Long orderId) {
    Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(
        orderId));
    return order;
  }
}
