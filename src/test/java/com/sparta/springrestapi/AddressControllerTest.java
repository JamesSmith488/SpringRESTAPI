package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.AddressController;
import com.sparta.springrestapi.entities.AddressEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class AddressControllerTest {

    @Autowired
    private AddressController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }

    @Test
    @DisplayName("Checking address of address with id 1 test")
    void checkingAddressOfAddressWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        AddressEntity address = restTemplate.getForObject("http://localhost:8080/address/1", AddressEntity.class);
        assert address != null;
        Assertions.assertEquals("47 MySakila Drive", address.getAddress());
    }

    @Test
    @DisplayName("Checking district of address with id 1 test")
    void checkingDistrictOfAddressWithId1Test() {
        RestTemplate restTemplate = new RestTemplate();
        AddressEntity address = restTemplate.getForObject("http://localhost:8080/address/1", AddressEntity.class);
        assert address != null;
        Assertions.assertEquals("Alberta", address.getDistrict());
    }

    @Test
    @DisplayName("Checking city id of address with id 5 test")
    void checkingCityIdOfAddressWithId5Test() {
        RestTemplate restTemplate = new RestTemplate();
        AddressEntity address = restTemplate.getForObject("http://localhost:8080/address/5", AddressEntity.class);
        assert address != null;
        Assertions.assertEquals(463, address.getCityId());
    }

    @Test
    @DisplayName("Checking postal code of address with id 5 test")
    void checkingPostalCodeOfAddressWithId5Test() {
        RestTemplate restTemplate = new RestTemplate();
        AddressEntity address = restTemplate.getForObject("http://localhost:8080/address/5", AddressEntity.class);
        assert address != null;
        Assertions.assertEquals("35200", address.getPostalCode());
    }

    @Test
    @DisplayName("Checking phone number of address with id 5 test")
    void checkingPhoneNumberOfAddressWithId5Test() {
        RestTemplate restTemplate = new RestTemplate();
        AddressEntity address = restTemplate.getForObject("http://localhost:8080/address/5", AddressEntity.class);
        assert address != null;
        Assertions.assertEquals("28303384290", address.getPhone());
    }

}