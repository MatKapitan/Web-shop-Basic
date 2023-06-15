package com.matkap.webshopbasics.exception.notFound;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(Long id) {
        super("The Customer with id '"+ id + "' does not exist in our records");
    }
}
