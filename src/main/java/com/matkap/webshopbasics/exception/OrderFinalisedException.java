package com.matkap.webshopbasics.exception;

public class OrderFinalisedException extends RuntimeException{


    public OrderFinalisedException(Long id) {
        super("The Order with id '"+ id + "' has already been finalised");
    }
}
