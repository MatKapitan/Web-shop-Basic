package com.matkap.webshopbasics.dto;

import com.matkap.webshopbasics.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String code;
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotNull(message = "price cannot be empty")
    @DecimalMin(value = "0.00" , inclusive = false, message = "price cannot be negative")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal priceEur;
    @NotBlank(message = "description cannot be blank")
    private String description;
    private boolean isAvailable;

    private Integer quantity;

    public ProductDto() {
        this.code = RandomStringUtils.randomAlphabetic(10);
    }

    private ProductDto(Product product){
        this.id = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.priceEur = product.getPriceEur();
        this.description = product.getDescription();
        this.isAvailable = product.is_available();
    }
    private ProductDto(Product product, Integer quantity){
        this(product);
        this.quantity = quantity;
    }

    public static ProductDto from(Product product){
        return new ProductDto(product);
    }
    public static ProductDto from(Product product, Integer quantity){
        return new ProductDto(product, quantity);
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }



}
