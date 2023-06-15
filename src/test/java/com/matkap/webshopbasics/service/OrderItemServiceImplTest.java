package com.matkap.webshopbasics.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.matkap.webshopbasics.dto.QuantityRequest;
import com.matkap.webshopbasics.entity.Customer;
import com.matkap.webshopbasics.entity.Order;
import com.matkap.webshopbasics.entity.Product;
import com.matkap.webshopbasics.entity.StatusEnum;
import com.matkap.webshopbasics.exception.OrderFinalisedException;
import com.matkap.webshopbasics.exception.ProductAlreadyAddedException;
import com.matkap.webshopbasics.exception.notFound.OrderNotFoundException;
import com.matkap.webshopbasics.exception.notFound.ProductNotFoundException;
import com.matkap.webshopbasics.repository.OrderItemRepository;
import com.matkap.webshopbasics.repository.OrderRepository;
import com.matkap.webshopbasics.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {
  // createOrderItem - normal,
  //delete - normal

  @Mock
  private OrderItemRepository orderItemRepository;

  @Mock
  OrderRepository orderRepository;

  @Mock
  ProductRepository productRepository;

  @InjectMocks
  private OrderItemServiceImpl orderItemService;


  @Test
  public void createOrderItem_givenOrderItemDto_shouldCreateOrderItem() {
    QuantityRequest quantityRequest = new QuantityRequest(2);
    Order order = new Order(new Customer(), StatusEnum.DRAFT);
    Product product = new Product("Apple", BigDecimal.valueOf(1.23), "Red apple", true);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(productRepository.findByIdAndAvailable(0L, true)).thenReturn(Optional.of(product));
    when(orderItemRepository.existsByProduct_IdAndOrder_Id(0L, 1L)).thenReturn(false);

    orderItemService.addProductOnOrder(quantityRequest, 1L, 0L);

  }

  @Test
  public void createOrderItem_nonExistentOrder_shouldThrowException() {
    QuantityRequest quantity = new QuantityRequest(1);

    assertThatExceptionOfType(OrderNotFoundException.class)
        .isThrownBy(() -> this.orderItemService.addProductOnOrder(quantity, 1L, 0L))
        .withMessage("The Order with id '1' does not exist in our records");
  }
  // --------------------------------------------

  @Test
  public void createOrderItem_orderItemAlreadySubmitted_shouldThrowException() {
    QuantityRequest quantity = new QuantityRequest(1);
    Order order = new Order(new Customer(), StatusEnum.SUBMITTED);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    assertThatExceptionOfType(OrderFinalisedException.class)
        .isThrownBy(() -> this.orderItemService.addProductOnOrder(quantity, 1L, 0L))
        .withMessage("The Order with id '" + 1 + "' has already been finalised");
  }

  ///-----------------------------
  @Test
  public void createOrderItem_nonExistentOrAvailableProduct_shouldThrowException() {
    QuantityRequest quantity = new QuantityRequest(1);
    Order order = new Order(new Customer(), StatusEnum.DRAFT);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

    assertThatExceptionOfType(ProductNotFoundException.class)
        .isThrownBy(() -> this.orderItemService.addProductOnOrder(quantity, 1L, 0L))
        .withMessage("The Product with id '" + 0 + "' does not exist in our records");
  }

  @Test
  public void createOrderItem_productAlreadyUsed_shouldThrowException() {
    QuantityRequest quantity = new QuantityRequest(1);
    Order order = new Order(new Customer(), StatusEnum.DRAFT);
    Product product = new Product("Apple", BigDecimal.valueOf(1.23), "Red apple", true);

    when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
    when(productRepository.findByIdAndAvailable(0L, true)).thenReturn(Optional.of(product));
    when(orderItemRepository.existsByProduct_IdAndOrder_Id(0L, 1L)).thenReturn(true);

    assertThatExceptionOfType(ProductAlreadyAddedException.class)
        .isThrownBy(() -> this.orderItemService.addProductOnOrder(quantity, 1L, 0L))
        .withMessage("Can't add same product twice");
  }

  @Test
  public void deleteCustomer_elementsExist_shouldDeleteEntity() {
    doNothing().when(orderItemRepository).deleteById(anyLong());

    orderItemService.deleteOrderItem(anyLong(), anyLong());
    verify(orderItemRepository, times(1)).deleteById(anyLong());
    verifyNoMoreInteractions(orderItemRepository);
  }

  @Test
  public void updateOrderItem() {
  }


}