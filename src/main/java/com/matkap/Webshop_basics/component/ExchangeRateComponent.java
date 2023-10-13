package com.matkap.Webshop_basics.component;

import com.matkap.Webshop_basics.pojo.CurrencyListPojo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class ExchangeRateComponent {

    public BigDecimal getMiddleExchangeRate(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CurrencyListPojo[]> response = restTemplate.getForEntity(url, CurrencyListPojo[].class);
        CurrencyListPojo[] currencyList = response.getBody();
        CurrencyListPojo currencyListPojo = Objects.requireNonNull(currencyList, "Exchange rate can't be react")[0];

        String x = currencyListPojo.getSrednji_tecaj().replace(',', '.');
        return new BigDecimal(x);
    }
}
