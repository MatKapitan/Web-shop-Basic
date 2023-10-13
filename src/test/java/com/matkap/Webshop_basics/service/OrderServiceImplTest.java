package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.component.ExchangeRateComponent;
import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.StatusEnum;
import com.matkap.Webshop_basics.exception.notFound.OrderNotFoundException;
import com.matkap.Webshop_basics.pojo.CurrencyListPojo;
import com.matkap.Webshop_basics.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final String HNB_EUR_TO_USD_EXCHANGE_RATE_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";

    //orderById
    @Mock
    OrderRepository orderRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ExchangeRateComponent exchangeRateComponent;

    @InjectMocks
    OrderServiceImpl orderService;


    @Test
    public void getOrderById_elementsExist_shouldFindAndReturnOneCustomer(){


        Order order = new Order(new Customer(), StatusEnum.DRAFT);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDto result = orderService.getOrderById(1L);

        assertEquals(StatusEnum.DRAFT, result.getStatusEnum());

        verify(orderRepository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void getOrderById_nullElement_shouldThrowException() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderService.getOrderById(null))
                .withMessage("The Order with id '"+ null + "' does not exist in our records");
    }

    @Test
    public void getOrderById_nonExistentOrder_shouldThrowException() {
        assertThatExceptionOfType(OrderNotFoundException.class)
                .isThrownBy(() -> this.orderService.getOrderById(1L))
                .withMessage("The Order with id '"+ 1L + "' does not exist in our records");
    }
    @Test
    public void getAllOrders_elementsExist_shouldReturnAll(){
        Customer customer_1 = new Customer("Ivan", "Kovac", "ivan.kovac@gmail.com");
        Customer customer_2 = new Customer("Velimir", "Sokolic", "velimir.sokolic@gmail.com");
        Order order_1 = new Order(customer_1, StatusEnum.DRAFT);
        Order order_2 = new Order(customer_2, StatusEnum.SUBMITTED);
        PageImpl page = new PageImpl<>(List.of(order_1,order_2));
        when(orderRepository.findAll(PageRequest.of(1 , 10))).thenReturn(page);

        Page<OrderDto> result = orderService.getAllOrders(PageRequest.of(1,10));

        assertEquals(2, result.getSize());
        assertEquals("Ivan", result.getContent().get(0).getCustomer().getFirst_name());
        assertEquals("Kovac", result.getContent().get(0).getCustomer().getLast_name());
        assertEquals("ivan.kovac@gmail.com", result.getContent().get(0).getCustomer().getEmail());
        assertEquals(StatusEnum.DRAFT, result.getContent().get(0).getStatusEnum());
        assertEquals("Velimir", result.getContent().get(1).getCustomer().getFirst_name());
        assertEquals("Sokolic", result.getContent().get(1).getCustomer().getLast_name());
        assertEquals("velimir.sokolic@gmail.com", result.getContent().get(1).getCustomer().getEmail());
        assertEquals(StatusEnum.SUBMITTED, result.getContent().get(1).getStatusEnum());
        verify(orderRepository, only()).findAll(PageRequest.of(1 , 10));
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void deleteOrder_elementExist_shouldDeleteOrder(){
        doNothing().when(orderRepository).deleteById(anyLong());

        orderService.deleteOrder(anyLong());
        verify(orderRepository, times(1)).deleteById(anyLong());
        verifyNoMoreInteractions(orderRepository);
    }

    @Test
    public void finaliseOrder_elementExist_shouldFinaliseOrder() throws IOException {

        CurrencyListPojo[] currencyListPojoArray =  new CurrencyListPojo[1];

        Order order = new Order(new Customer(),StatusEnum.DRAFT);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.getFinalPrice(1L)).thenReturn(Optional.of(10.12));
        when(exchangeRateComponent.getMiddleExchangeRate(HNB_EUR_TO_USD_EXCHANGE_RATE_URL)).thenReturn(new BigDecimal(1.0866));

        OrderDto result = orderService.finaliseOrder(1L);

        assertEquals(BigDecimal.valueOf(10.12), result.getTotalPriceEur());
        assertEquals(StatusEnum.SUBMITTED, result.getStatusEnum());

    }







}