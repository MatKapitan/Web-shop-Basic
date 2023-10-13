package com.matkap.Webshop_basics.dto;

import com.matkap.Webshop_basics.entity.Customer;
import com.matkap.Webshop_basics.entity.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class CustomerDto {

    private Long id;
    @NotBlank(message="First name must not be null and contain 4 characters or more")
    @Size(min = 4, message = "First name must contain 4 characters or more")
    private String first_name;
    @NotBlank(message="Last name must not be null and contain 4 characters or more")
    @Size(min = 4, message = "Last name must contain 4 characters or more")
    private String last_name;
    @Email(message="Email should be valid")
    @NotBlank(message="Email must not be null and contain valid email")
    private String email;

    private Set<Order> order;


    public CustomerDto(){}

    public CustomerDto(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public CustomerDto(Customer customer) {
        this.id = customer.getId();
        this.first_name = customer.getFirst_name();
        this.last_name = customer.getLast_name();
        this.email = customer.getEmail();
        this.order = customer.getOrder();
    }

    public static CustomerDto from(Customer customer) {
        return new CustomerDto(customer);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Order> getOrder() {
        return order;
    }

    public void setOrder(Set<Order> order) {
        this.order = order;
    }


}
