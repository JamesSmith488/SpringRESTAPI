package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.CityEntity;
import com.sparta.springrestapi.exceptions.CityNotFoundException;
import com.sparta.springrestapi.repositories.CityRepository;
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
public class CityController {

    private final CityRepository repository;

    @Autowired
    public CityController(CityRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/city/all")
    public CollectionModel<EntityModel<CityEntity>> findAllCities() {
        List<EntityModel<CityEntity>> cities = repository.findAll().stream()
                .map(city -> EntityModel.of(city,
                        linkTo(methodOn(CityController.class)
                                .findCityById(city.getCityId()))
                                .withSelfRel(),
                        linkTo(methodOn(CountryController.class)
                                .findCountryById(city.getCountryId()))
                                .withRel("country")))
                .toList();
        return CollectionModel.of(cities,
                linkTo(methodOn(CityController.class)
                        .findAllCities())
                        .withSelfRel());
    }

    @GetMapping("/city/{id}")
    public EntityModel<CityEntity> findCityById(@PathVariable("id") Integer id) {
        CityEntity city = repository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
        return EntityModel.of(city,
                linkTo(methodOn(CityController.class)
                        .findCityById(id))
                        .withSelfRel(),
                linkTo(methodOn(CountryController.class)
                        .findCountryById(city.getCountryId()))
                        .withRel("country"),
                linkTo(methodOn(CityController.class)
                        .findAllCities())
                        .withRel("allCities"));
    }

    @GetMapping("/city")
    public CollectionModel<EntityModel<CityEntity>> findCitiesByName(@RequestParam(required = false) String name) {
        List<EntityModel<CityEntity>> foundCities;
        if(name == null) {
            return findAllCities();
        } else {
            foundCities = new ArrayList<>();
            for(CityEntity city : repository.findAll()) {
                if(city.getCity().toLowerCase().contains(name.toLowerCase())) {
                    foundCities.add(EntityModel.of(city,
                            linkTo(methodOn(CityController.class)
                                    .findCityById(city.getCityId()))
                                    .withSelfRel(),
                            linkTo(methodOn(CountryController.class)
                                    .findCountryById(city.getCountryId()))
                                    .withRel("country")));
                }
            }
        }
        return CollectionModel.of(foundCities,
                linkTo(methodOn(CityController.class)
                        .findCitiesByName(name))
                        .withSelfRel());
    }

    @GetMapping("/city/country/{id}")
    public CollectionModel<EntityModel<CityEntity>> findCityByCountryId(@PathVariable("id") Integer id) {
        List<EntityModel<CityEntity>> cities = repository.findByCountryId(id).stream()
                .map(city -> EntityModel.of(city,
                        linkTo(methodOn(CityController.class)
                                .findCityById(city.getCityId()))
                                .withSelfRel(),
                        linkTo(methodOn(CountryController.class)
                                .findCountryById(city.getCountryId()))
                                .withRel("country")))
                .toList();
        return CollectionModel.of(cities,
                linkTo(methodOn(CityController.class)
                        .findCityByCountryId(id))
                        .withSelfRel());
    }

    @PostMapping("/city")
    public EntityModel<CityEntity> addCity(@RequestBody CityEntity city) throws ValidationException {
        if(city.getCityId() == null && city.getCity() != null && city.getCountryId() != null && city.getLastUpdate() != null) {
            return EntityModel.of(city,
                    linkTo(methodOn(CityController.class)
                            .findCityByCountryId(city.getCityId()))
                            .withSelfRel(),
                    linkTo(methodOn(CountryController.class)
                            .findCountryById(city.getCountryId()))
                            .withRel("country"),
                    linkTo(methodOn(CityController.class)
                            .findAllCities())
                            .withRel("allCities"));
        } else {
            throw new ValidationException("City Cannot Be Created");
        }
    }

    @PutMapping("/city")
    public ResponseEntity<CityEntity> updateCity(@RequestBody CityEntity city) {
        if(repository.findById(city.getCityId()).isPresent()){
            return new ResponseEntity<>(repository.save(city), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(city, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity<?> deleteCity(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}