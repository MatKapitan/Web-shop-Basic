package com.matkap.Webshop_basics.exception.notFound;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Long id) {
        super("The Customer with id '"+ id + "' does not exist in our records");
    }
}
