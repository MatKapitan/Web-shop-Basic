package com.matkap.Webshop_basics.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class ShopController {


    @GetMapping
    public String hello(){
        return "hello local time is " + LocalDateTime.now();
    }
}
