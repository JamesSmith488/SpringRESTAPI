package com.sparta.springrestapi.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Integer id) {
        super("Could not find address: " + id);
    }

    public AddressNotFoundException(String code) {
        super("Could not find address: " + code);
    }

}