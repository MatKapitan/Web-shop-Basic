package com.matkap.Webshop_basics.service;

import com.matkap.Webshop_basics.component.ExchangeRateComponent;
import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.entity.StatusEnum;
import com.matkap.Webshop_basics.exception.ActiveOrderExistException;
import com.matkap.Webshop_basics.exception.FinalPriceIsNullException;
import com.matkap.Webshop_basics.exception.notFound.CustomerNotFoundException;
import com.matkap.Webshop_basics.exception.notFound.OrderNotFoundException;
import com.matkap.Webshop_basics.repository.CustomerRepository;
import com.matkap.Webshop_basics.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String HNB_EUR_TO_USD_EXCHANGE_RATE_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ExchangeRateComponent exchangeRateComponent;


    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return orderToDto(order);

    }

    @Override
    public Page<OrderDto> getAllOrders(Pageable page) {
        Page<Order> orders = orderRepository.findAll(page);
        return orders.map(OrderDto::from);
    }

    @Override
    public Long createOrder(Long customer_id) {
        Customer customer = customerRepository.findById(customer_id).orElseThrow(()-> new CustomerNotFoundException(customer_id));
        if(orderRepository.existsByCustomer_IdAndStatusEnum(customer_id, StatusEnum.DRAFT)) throw new ActiveOrderExistException();
        Order order = new Order();
        order.setStatusEnum(StatusEnum.DRAFT);
        order.setCustomer(customer);
        Order savedOrder = orderRepository.save(order);
        return savedOrder.getId();
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);

    }
    @Override
    public OrderDto finaliseOrder(Long id) {
        Objects.requireNonNull(id, "id must not be null");
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        Double finalPriceEur = orderRepository.getFinalPrice(id).orElseThrow(FinalPriceIsNullException::new);
        BigDecimal eurToUsdExchange = exchangeRateComponent.getMiddleExchangeRate(HNB_EUR_TO_USD_EXCHANGE_RATE_URL);
        order.setTotalPriceEur(BigDecimal.valueOf(finalPriceEur));
        order.setTotalPriceUsd((order.getTotalPriceEur().multiply(eurToUsdExchange)).setScale(2, RoundingMode.HALF_UP));
        order.setStatusEnum(StatusEnum.SUBMITTED);
        orderRepository.save(order);
        return orderToDto(order);

    }

    //        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<CurrencyListPojo[]> response = restTemplate.getForEntity("https://api.hnb.hr/tecajn-eur/v3?valuta=USD", CurrencyListPojo[].class);
//        CurrencyListPojo[] tecajnaLista = response.getBody();
//        CurrencyListPojo currencyListPojo = Objects.requireNonNull(tecajnaLista)[0];
//
//
//        String x = currencyListPojo.getSrednji_tecaj().replace(',','.');
//        System.out.println(Float.parseFloat(x));

    public Order orderDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setCustomer(orderDto.getCustomer());
        order.setStatusEnum(orderDto.getStatusEnum());
        order.setTotalPriceEur(orderDto.getTotalPriceEur());
        order.setTotalPriceUsd(orderDto.getTotalPriceUsd());
        return order;
    }

    public OrderDto orderToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setCustomer(order.getCustomer());
        orderDto.setStatusEnum(order.getStatusEnum());
        orderDto.setTotalPriceEur(order.getTotalPriceEur());
        orderDto.setTotalPriceUsd(order.getTotalPriceUsd());
        return orderDto;
    }



}
