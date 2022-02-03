package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.CountryEntity;
import com.sparta.springrestapi.exceptions.CountryNotFoundException;
import com.sparta.springrestapi.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CountryController {

    private final CountryRepository repository;

    @Autowired
    public CountryController(CountryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/country/all")
    public CollectionModel<EntityModel<CountryEntity>> findAllCountries() {
        List<EntityModel<CountryEntity>> countries = repository.findAll().stream()
                .map(country -> EntityModel.of(country,
                        linkTo(methodOn(CountryController.class)
                                .findCountryById(country.getCountryId()))
                                .withSelfRel()))
                .toList();
        return CollectionModel.of(countries,
                linkTo(methodOn(CountryController.class)
                        .findAllCountries())
                        .withSelfRel());
    }

    @GetMapping("/country/{id}")
    public EntityModel<CountryEntity> findCountryById(@PathVariable("id") Integer id) {
        CountryEntity country = repository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException(id));
        return EntityModel.of(country,
                linkTo(methodOn(CountryController.class)
                        .findCountryById(id))
                        .withSelfRel(),
                linkTo(methodOn(CountryController.class)
                        .findAllCountries())
                        .withRel("allCountries"));
    }

    @GetMapping("/country")
    public CollectionModel<EntityModel<CountryEntity>> findCountriesByName(@RequestParam(required = false) String name) {
        List<EntityModel<CountryEntity>> foundCountries;
        if(name == null) {
            return findAllCountries();
        } else {
            foundCountries = new ArrayList<>();
            for(CountryEntity country : repository.findAll()) {
                if(country.getCountry().toLowerCase().contains(name.toLowerCase())) {
                    foundCountries.add(EntityModel.of(country,
                            linkTo(methodOn(CountryController.class)
                                    .findCountryById(country.getCountryId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundCountries,
                linkTo(methodOn(CountryController.class)
                        .findCountriesByName(name))
                        .withSelfRel());
    }

    @PostMapping("/country")
    public EntityModel<CountryEntity> addCountry(@RequestBody CountryEntity country) throws ValidationException {
        if(country.getCountryId() == null && country.getCountryId() != null && country.getLastUpdate() != null) {
            return EntityModel.of(country,
                    linkTo(methodOn(CountryController.class)
                            .findCountryById(country.getCountryId()))
                            .withSelfRel(),
                    linkTo(methodOn(CountryController.class)
                            .findAllCountries())
                            .withRel("allCountries"));
        } else {
            throw new ValidationException("Country Cannot Be Created");
        }
    }

    @PutMapping("/country")
    public ResponseEntity<CountryEntity> updateCountry(@RequestBody CountryEntity country) {
        if(repository.findById(country.getCountryId()).isPresent()) {
            return new ResponseEntity<>(repository.save(country), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(country, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/country/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}