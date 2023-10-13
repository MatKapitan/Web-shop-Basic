package com.matkap.Webshop_basics.integration;


import com.matkap.Webshop_basics.dto.ProductDto;
import com.matkap.Webshop_basics.exception.notFound.ProductNotFoundException;
import com.matkap.Webshop_basics.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SqlGroup({
        @Sql(value = "classpath:restart/product-restart.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "classpath:init/product-data.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class ProductPostgresSQLTest extends AbstractIntegrationTest{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void should_retrieve_one_product() throws Exception{

        mockMvc.perform(get("/product/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void shouldRetrieveOneProduct_throws_exception() throws Exception{

        mockMvc.perform(get("/product/10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ProductNotFoundException))
                .andExpect(result -> assertEquals("The Product with id '10' does not exist in our records", result.getResolvedException().getMessage()));
    }

    @Test
    void should_retrieve_all_products() throws Exception {

        mockMvc.perform(get("/product/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.[0].id").value(1))
                .andExpect(jsonPath("$.content.[0].name").value("Apple"))
                .andExpect(jsonPath("$.content.[1].id").value(2))
                .andExpect(jsonPath("$.content.[1].name").value("Asparagus"))

                .andExpect(jsonPath("$.content.[2].id").value(3))
                .andExpect(jsonPath("$.content.[2].name").value("Olives"))

                .andExpect(jsonPath("$.content.[3].id").value(4))
                .andExpect(jsonPath("$.content.[3].name").value("Tomatoes"))

                .andExpect(jsonPath("$.content.[4].id").value(5))
                .andExpect(jsonPath("$.content.[4].name").value("Raspberries"));



    }


    @Test
    void should_save_product() throws Exception {
        ProductDto productDto = new ProductDto();
        productDto.setName("Kruska");
        productDto.setPriceEur(BigDecimal.valueOf(23.22));
        productDto.setDescription("bla bla");

        mockMvc.perform(post("/product")
                        .content(new ObjectMapper().writeValueAsString(productDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    void should_delete_one_product() throws Exception {
        this.mockMvc.perform(delete("/product/delete/{id}", 2))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertThat(this.productRepository.findAll()).hasSize(4);
    }

    }
