package com.matkap.Webshop_basics.exception;

public class ActiveOrderExistException extends RuntimeException {

    public ActiveOrderExistException(){
        super("Active order already exists");
    }

}
