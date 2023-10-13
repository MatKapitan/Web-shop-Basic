package com.matkap.Webshop_basics.controller;

import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<OrderDto>> getAllOrders(@PageableDefault(value = 5, page = 0) Pageable page){
        return new ResponseEntity<>(orderService.getAllOrders(page), HttpStatus.OK);
    }


    @PostMapping("/{customer_id}")
    public ResponseEntity<String> createOrder(@PathVariable Long customer_id){
        Long new_id = orderService.createOrder(customer_id);
        return new ResponseEntity<>(String.format("new order with id '%s' has been created",new_id),HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/finalise/{id}")
    public ResponseEntity<OrderDto> calculateFinalPrice(@PathVariable Long id){
        return new ResponseEntity<>(orderService.finaliseOrder(id),HttpStatus.OK);

    }





}
