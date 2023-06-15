package com.matkap.webshopbasics.dto;

import jakarta.validation.constraints.Positive;

public class QuantityRequest {

    @Positive(message = "Quantity cannot be 0 or negative")
    private int quantity;

    public QuantityRequest() {
    }

    public QuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
