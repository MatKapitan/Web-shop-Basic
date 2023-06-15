package com.matkap.webshopbasics.exception;

public class ProductAlreadyAddedException extends RuntimeException{

    public ProductAlreadyAddedException() {
        super("Can't add same product twice");
    }

}
