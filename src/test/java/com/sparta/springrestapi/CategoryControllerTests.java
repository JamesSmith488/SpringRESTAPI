package com.sparta.springrestapi;

import com.sparta.springrestapi.controller.CategoryController;
import com.sparta.springrestapi.entities.CategoryEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CategoryControllerTests {

    @Autowired
    private CategoryController controller;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(controller);
    }
    
    @Test
    @DisplayName("Checking category with id 1 is called action test")
    void checkingCategoryWithId1IsCalledActionTest() {
        RestTemplate restTemplate = new RestTemplate();
        CategoryEntity category = restTemplate.getForObject("http://localhost:8080/category/1", CategoryEntity.class);
        assert category != null;
        Assertions.assertEquals("Action", category.getName());
    }

}