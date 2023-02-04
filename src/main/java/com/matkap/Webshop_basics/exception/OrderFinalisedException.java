package com.matkap.Webshop_basics.exception;

public class OrderFinalisedException extends RuntimeException{


    public OrderFinalisedException(Long id) {
        super("The Order with id '"+ id + "' has already been finalised");
    }
}
