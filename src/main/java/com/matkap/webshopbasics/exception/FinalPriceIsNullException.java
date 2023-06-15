package com.matkap.webshopbasics.exception;

public class FinalPriceIsNullException  extends RuntimeException{

    public FinalPriceIsNullException(){
        super("The Order has no ordered items");
    }
}
