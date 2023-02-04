package com.matkap.Webshop_basics.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;

import java.math.BigDecimal;
import java.security.AllPermission;
import java.util.List;

@Entity
@Table(name = "webshop_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @Enumerated(EnumType.STRING)
    @Column(name = "statusEnum")
    private StatusEnum statusEnum;
    @Column(name = "totalPriceEur")
    private BigDecimal totalPriceEur;
    @Column(name = "totalPriceUsd")
    private BigDecimal totalPriceUsd;

    @JsonIgnore
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList;


    public Order() {
    }

    public Order(Customer customer, StatusEnum statusEnum) {
        this.customer = customer;
        this.statusEnum = statusEnum;
    }

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

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
