package com.sparta.springrestapi.exceptions;

public class CountryNotFoundException extends RuntimeException {

    public CountryNotFoundException(Integer id) {
        super("Could not find country: " + id);
    }

}