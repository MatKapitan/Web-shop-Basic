package com.matkap.webshopbasics.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "priceEur")
    private BigDecimal priceEur;
    @Column(name = "description")
    private String description;
    @Column(name = "isAvailable")
    private boolean isAvailable;

    @OneToOne(mappedBy = "product", cascade = CascadeType.REMOVE)
    private OrderItem orderItem;

    public Product() {
        this.code = RandomStringUtils.randomAlphabetic(10);
    }
    public Product(String name, BigDecimal priceEur, String description, boolean isAvailable) {
        this.name = name;
        this.code = RandomStringUtils.randomAlphabetic(10);
        this.priceEur = priceEur;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceEur() {
        return priceEur;
    }

    public void setPriceEur(BigDecimal priceEur) {
        this.priceEur = priceEur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @JsonProperty(value="is_available")
    public boolean is_available() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }


}
