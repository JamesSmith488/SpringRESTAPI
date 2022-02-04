package com.sparta.springrestapi;

import com.sparta.springrestapi.entities.AddressEntity;
import com.sparta.springrestapi.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTests {
    
    @Autowired
    private AddressRepository repository;
    
    @Test
    @DisplayName("Checking there are 603 addresses test")
    void checkingThereAre605AddressesTest() {
        List<AddressEntity> all = repository.findAll();
        Assertions.assertEquals(603, all.size());
    }
    
}