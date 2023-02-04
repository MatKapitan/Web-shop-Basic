package com.matkap.Webshop_basics.exception.notFound;

public class OrderItemNotFoundException extends RuntimeException{

    public OrderItemNotFoundException(Long order_id, Long product_id) {
        super("The Order item with order id '"+ order_id + "' and product id '"+ product_id + "' does not exist in our records");
    }
}
