package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.OrderDto;
import com.matkap.webshopbasics.entity.Customer;
import com.matkap.webshopbasics.entity.Order;
import com.matkap.webshopbasics.entity.StatusEnum;
import com.matkap.webshopbasics.exception.ActiveOrderExistException;
import com.matkap.webshopbasics.exception.FinalPriceIsNullException;
import com.matkap.webshopbasics.exception.notFound.CustomerNotFoundException;
import com.matkap.webshopbasics.exception.notFound.OrderNotFoundException;
import com.matkap.webshopbasics.pojo.CurrencyListPojo;
import com.matkap.webshopbasics.repository.CustomerRepository;
import com.matkap.webshopbasics.repository.OrderRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderServiceImpl implements OrderService {

  public static final String HNB_EUR_TO_USD_EXCHANGE_RATE_URL = "https://api.hnb.hr/tecajn-eur/v3?valuta=USD";

  private final OrderRepository orderRepository;

  private final CustomerRepository customerRepository;

  public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
  }


  @Override
  public OrderDto getById(Long id) {
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    return orderToDto(order);

  }

  @Override
  public Page<OrderDto> getAll(Pageable page) {
    return orderRepository.findAll(page).map(this::orderToDto);
  }

  @Override
  public OrderDto create(Long id) {
    Objects.requireNonNull(id, "id must not be null");
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(id));
    if (orderRepository.existsByCustomer_IdAndStatusEnum(id, StatusEnum.DRAFT)) {
      throw new ActiveOrderExistException();
    }
    Order order = new Order();
    order.setStatusEnum(StatusEnum.DRAFT);
    order.setCustomer(customer);
    return orderToDto(orderRepository.save(order));
  }

  @Override
  public void delete(Long id) {
    orderRepository.deleteById(id);
  }

  @Override
  public void finalise(Long id) {
    Objects.requireNonNull(id, "id must not be null");
    Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    Double finalPriceEur = orderRepository.getFinalPrice(id)
        .orElseThrow(FinalPriceIsNullException::new);
    BigDecimal eurToUsdExchange = getCurrencyListMiddleFloat();
    order.setTotalPriceEur(BigDecimal.valueOf(finalPriceEur));
    order.setTotalPriceUsd(
        (order.getTotalPriceEur().multiply(eurToUsdExchange)).setScale(2, RoundingMode.HALF_UP));
    order.setStatusEnum(StatusEnum.SUBMITTED);
    orderRepository.save(order);
    orderToDto(order);

  }


  private BigDecimal getCurrencyListMiddleFloat() {
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<CurrencyListPojo[]> response = restTemplate.getForEntity(
        HNB_EUR_TO_USD_EXCHANGE_RATE_URL, CurrencyListPojo[].class);
    CurrencyListPojo[] currencyList = response.getBody();
    CurrencyListPojo currencyListPojo = Objects.requireNonNull(currencyList)[0];

    String x = currencyListPojo.getSrednji_tecaj().replace(',', '.');
    return new BigDecimal(x);
  }

  //        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<CurrencyListPojo[]> response = restTemplate.getForEntity("https://api.hnb.hr/tecajn-eur/v3?valuta=USD", CurrencyListPojo[].class);
//        CurrencyListPojo[] tecajnaLista = response.getBody();
//        CurrencyListPojo currencyListPojo = Objects.requireNonNull(tecajnaLista)[0];
//
//
//        String x = currencyListPojo.getSrednji_tecaj().replace(',','.');
//        System.out.println(Float.parseFloat(x));

  public Order orderDtoToEntity(OrderDto orderDto) {
    Order order = new Order();
    order.setId(orderDto.getId());
    order.setCustomer(orderDto.getCustomer());
    order.setStatusEnum(orderDto.getStatusEnum());
    order.setTotalPriceEur(orderDto.getTotalPriceEur());
    order.setTotalPriceUsd(orderDto.getTotalPriceUsd());
    return order;
  }

  public OrderDto orderToDto(Order order) {
    OrderDto orderDto = new OrderDto();
    orderDto.setId(order.getId());
    orderDto.setCustomer(order.getCustomer());
    orderDto.setStatusEnum(order.getStatusEnum());
    orderDto.setTotalPriceEur(order.getTotalPriceEur());
    orderDto.setTotalPriceUsd(order.getTotalPriceUsd());
    return orderDto;
  }


}
