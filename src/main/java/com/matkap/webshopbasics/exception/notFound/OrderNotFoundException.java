package com.matkap.webshopbasics.exception.notFound;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(Long id) {
        super("The Order with id '"+ id + "' does not exist in our records");
    }
}
