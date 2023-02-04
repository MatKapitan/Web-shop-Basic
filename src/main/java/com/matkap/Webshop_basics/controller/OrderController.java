package com.matkap.Webshop_basics.controller;

import com.matkap.Webshop_basics.dto.OrderDto;
import com.matkap.Webshop_basics.entity.Order;
import com.matkap.Webshop_basics.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
        return new ResponseEntity<>(orderService.getOrderById(id), HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<Page<Order>> getAllOrders(@PageableDefault(value = 5, page = 0) Pageable page){
        return new ResponseEntity<>(orderService.getAllOrders(page), HttpStatus.OK);
    }


    @PostMapping("/{customer_id}")
    public ResponseEntity<HttpStatus> createOrder(@PathVariable Long customer_id){
        orderService.createOrder(customer_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
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
