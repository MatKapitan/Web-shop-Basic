package com.matkap.webshopbasics.exception.notFound;

public class ProductNotFoundException extends RuntimeException {


    public ProductNotFoundException(Long id) {
        super("The Product with id '"+ id + "' does not exist in our records");
    }
}
