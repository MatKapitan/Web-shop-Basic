package com.matkap.webshopbasics.service;

import com.matkap.webshopbasics.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

  OrderDto getById(Long id);

  Page<OrderDto> getAll(Pageable page);

  OrderDto create(Long id);

  void delete(Long id);

  void finalise(Long id);


}
