package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.CityController;
import com.sparta.springrestapi.entities.CityEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CityControllerTests {
    
    @Autowired
    private CityController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking city with id 312 is London test")
    void checkingCityWithId312IsLondonTest() {
        RestTemplate restTemplate = new RestTemplate();
        CityEntity city = restTemplate.getForObject("http://localhost:8080/city/312", CityEntity.class);
        assert city != null;
        Assertions.assertEquals("London", city.getCity());
    }

    @Test
    @DisplayName("Checking city with id 312 has a country id of 102 test")
    void checkingCityWithId312HasACountryIdOf102Test() {
        RestTemplate restTemplate = new RestTemplate();
        CityEntity city = restTemplate.getForObject("http://localhost:8080/city/312", CityEntity.class);
        assert city != null;
        Assertions.assertEquals(102, city.getCountryId());
    }
    
}