package com.sparta.springrestapi.exceptions;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(Integer id) {
        super("Could not find city: " + id);
    }

}