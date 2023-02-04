package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.dto.QuantityRequest;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.Product;
import com.matkap.Webshop_basics.entity.StatusEnum;
import com.matkap.Webshop_basics.exception.OrderFinalisedException;
import com.matkap.Webshop_basics.exception.ProductAlreadyAddedException;
import com.matkap.Webshop_basics.exception.notFound.OrderNotFoundException;
import com.matkap.Webshop_basics.exception.notFound.ProductNotFoundException;
import com.matkap.Webshop_basics.repository.OrderItemRepository;
import com.matkap.Webshop_basics.repository.OrderRepository;
import com.matkap.Webshop_basics.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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
    public void createOrderItem_givenOrderItemDto_shouldCreateOrderItem(){
        QuantityRequest quantityRequest = new QuantityRequest(2);
        Order order = new Order(new Customer(), StatusEnum.DRAFT);
        Product product = new Product("Apple", BigDecimal.valueOf(1.23), "Red apple", true);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findByIdAndIsAvailable(0L, true)).thenReturn(Optional.of(product));
        when(orderItemRepository.existsByProduct_IdAndOrder_Id(0L, 1L)).thenReturn(false);

        orderItemService.createOrderItem(quantityRequest,1L,0L);

    }
    @Test
    public void createOrderItem_nonExistentOrder_shouldThrowException(){
        QuantityRequest quantity = new QuantityRequest(1);

        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderItemService.createOrderItem(quantity,1L, 0L))
                .withMessage("The Order with id '1' does not exist in our records");
    }
   // --------------------------------------------

    @Test
    public void createOrderItem_orderItemAlreadySubmitted_shouldThrowException(){
        QuantityRequest quantity = new QuantityRequest(1);
        Order order = new Order(new Customer(), StatusEnum.SUBMITTED);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThatExceptionOfType(OrderFinalisedException.class)
                .isThrownBy(() -> this.orderItemService.createOrderItem(quantity,1L, 0L))
                .withMessage("The Order with id '"+ 1 + "' has already been finalised");
    }
///-----------------------------
    @Test
    public void createOrderItem_nonExistentOrAvailableProduct_shouldThrowException(){
        QuantityRequest quantity = new QuantityRequest(1);
        Order order = new Order(new Customer(), StatusEnum.DRAFT);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> this.orderItemService.createOrderItem(quantity,1L, 0L))
                .withMessage("The Product with id '"+ 0 + "' does not exist in our records");
    }
    @Test
    public void createOrderItem_productAlreadyUsed_shouldThrowException(){
        QuantityRequest quantity = new QuantityRequest(1);
        Order order = new Order(new Customer(), StatusEnum.DRAFT);
        Product product = new Product("Apple", BigDecimal.valueOf(1.23), "Red apple", true);


        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(productRepository.findByIdAndIsAvailable(0L, true)).thenReturn(Optional.of(product));
        when(orderItemRepository.existsByProduct_IdAndOrder_Id(0L, 1L)).thenReturn(true);

        assertThatExceptionOfType(ProductAlreadyAddedException.class)
                .isThrownBy(() -> this.orderItemService.createOrderItem(quantity,1L, 0L))
                .withMessage("Can't add same product twice");
    }

    @Test
    public void deleteCustomer_elementsExist_shouldDeleteEntity() {
        doNothing().when(orderItemRepository).deleteById(anyLong());

        orderItemService.deleteOrderItem(anyLong());
        verify(orderItemRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(orderItemRepository);
    }

    @Test
    public void updateOrderItem(){
    }


}