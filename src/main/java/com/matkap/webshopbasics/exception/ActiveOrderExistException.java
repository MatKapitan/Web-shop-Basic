package com.matkap.webshopbasics.exception;

public class ActiveOrderExistException extends RuntimeException {

    public ActiveOrderExistException(){
        super("Active order already exists");
    }

}
