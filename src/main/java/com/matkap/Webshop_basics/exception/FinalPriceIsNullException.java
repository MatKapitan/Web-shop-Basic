package com.matkap.Webshop_basics.exception;

public class FinalPriceIsNullException  extends RuntimeException{

    public FinalPriceIsNullException(){
        super("The Order has no ordered items");
    }
}
