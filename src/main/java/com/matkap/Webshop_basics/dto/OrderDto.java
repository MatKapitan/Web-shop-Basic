package com.matkap.Webshop_basics.dto;

import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.StatusEnum;

import java.math.BigDecimal;

public class OrderDto {

    private Long id;
    private Customer customer;
    private StatusEnum statusEnum;
    private BigDecimal totalPriceEur;
    private BigDecimal totalPriceUsd;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

    public BigDecimal getTotalPriceEur() {
        return totalPriceEur;
    }

    public void setTotalPriceEur(BigDecimal totalPriceEur) {
        this.totalPriceEur = totalPriceEur;
    }

    public BigDecimal getTotalPriceUsd() {
        return totalPriceUsd;
    }

    public void setTotalPriceUsd(BigDecimal totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }
}
