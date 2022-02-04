package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.CountryController;
import com.sparta.springrestapi.entities.CountryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CountryControllerTests {

    @Autowired
    private CountryController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking country with id 102 is United Kingdom test")
    void checkingCountryWithId102IsUnitedKingdomTest() {
        RestTemplate restTemplate = new RestTemplate();
        CountryEntity country = restTemplate.getForObject("http://localhost:8080/country/102", CountryEntity.class);
        assert country != null;
        Assertions.assertEquals("United Kingdom", country.getCountry());
    }

}