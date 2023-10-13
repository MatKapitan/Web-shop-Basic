package com.matkap.Webshop_basics.controller;

import com.matkap.Webshop_basics.dto.QuantityRequest;
import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.service.OrderItemService;
import com.matkap.Webshop_basics.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order/{order_id}/product")
public class ProductForOrderController {

    @Autowired
    ProductService productService;
    @Autowired
    OrderItemService orderItemService;


    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> getOrderItemById(@PathVariable("order_id") Long id,
                                                       @PathVariable("product_id") Long productId){
        return new ResponseEntity<>(orderItemService.getOrderItemById(id, productId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAllOrderItem(@PathVariable(name = "order_id") Long id,
                                                            @PageableDefault(value = 5, page = 0) Pageable page){
        return ResponseEntity.ok(this.productService.getProductsByOrderId(id, page));
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<HttpStatus> createOrderItem(@Valid @RequestBody QuantityRequest quantityRequest,
                                                           @PathVariable(name = "order_id") Long order_id,
                                                           @PathVariable(name = "product_id") Long product_id){
        orderItemService.createOrderItem(quantityRequest, order_id, product_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{product_id}")
    public ResponseEntity<HttpStatus> deleteOrderItemById(@PathVariable(name = "product_id") Long id){
        orderItemService.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<HttpStatus> updateOrderItem(@Valid @RequestBody QuantityRequest quantityRequest,
                                                      @PathVariable(name = "order_id") Long order_id,
                                                      @PathVariable(name = "product_id") Long product_id) {
        orderItemService.updateOrderItem(quantityRequest, order_id, product_id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
