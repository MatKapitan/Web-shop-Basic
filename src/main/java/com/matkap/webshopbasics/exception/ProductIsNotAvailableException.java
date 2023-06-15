package com.matkap.webshopbasics.exception;

public class ProductIsNotAvailableException extends RuntimeException {

    public ProductIsNotAvailableException(Long id) {
        super("The Product with id '"+ id + "' is not available right now");
    }

}
