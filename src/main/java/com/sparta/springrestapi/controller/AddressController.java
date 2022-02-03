package com.sparta.springrestapi.controller;

import com.sparta.springrestapi.entities.AddressEntity;
import com.sparta.springrestapi.exceptions.AddressNotFoundException;
import com.sparta.springrestapi.repositories.AddressRepository;
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
public class AddressController {

    private final AddressRepository repository;

    @Autowired
    public AddressController(AddressRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/address/all")
    public CollectionModel<EntityModel<AddressEntity>> findAllAddresses() {
        List<EntityModel<AddressEntity>> addresses = repository.findAll().stream()
                .map(address -> EntityModel.of(address,
                        linkTo(methodOn(AddressController.class)
                                .findAddressById(address.getAddressId()))
                                .withSelfRel()))
                .toList();
        return CollectionModel.of(addresses,
                linkTo(methodOn(AddressController.class)
                        .findAllAddresses())
                        .withSelfRel());
    }

    @GetMapping("/address/{id}")
    public EntityModel<AddressEntity> findAddressById(@PathVariable("id") Integer id) {
        AddressEntity address = repository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class)
                        .findAllAddresses())
                        .withRel("allAddresses"),
                linkTo(methodOn(AddressController.class)
                        .findAddressById(id))
                        .withSelfRel());
    }

    @GetMapping("/address")
    public CollectionModel<EntityModel<AddressEntity>> findAddresses(@RequestParam(required = false) String address) {
        List<EntityModel<AddressEntity>> foundAddresses;
        if (address == null) {
            return findAllAddresses();
        } else {
            foundAddresses = new ArrayList<>();
            for(AddressEntity addressEntity : repository.findAll()) {
                if(addressEntity.getAddress().toLowerCase().contains(address.toLowerCase())) {
                    foundAddresses.add(EntityModel.of(addressEntity,
                            linkTo(methodOn(AddressController.class)
                                    .findAddressById(addressEntity.getAddressId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundAddresses,
                linkTo(methodOn(AddressController.class)
                        .findAddresses(address))
                        .withSelfRel());
    }

    @GetMapping("/address/district/{district}")
    public CollectionModel<EntityModel<AddressEntity>> findAddressesByDistrict(@PathVariable("district") String district) {
        List<EntityModel<AddressEntity>> foundAddresses;
        if (district == null) {
            return findAllAddresses();
        } else {
            foundAddresses = new ArrayList<>();
            for(AddressEntity addressEntity : repository.findAll()) {
                if(addressEntity.getDistrict().toLowerCase().contains(district.toLowerCase())) {
                    foundAddresses.add(EntityModel.of(addressEntity,
                            linkTo(methodOn(AddressController.class)
                                    .findAddressById(addressEntity.getAddressId()))
                                    .withSelfRel()));
                }
            }
        }
        return CollectionModel.of(foundAddresses,
                linkTo(methodOn(AddressController.class)
                        .findAddressesByDistrict(district))
                        .withSelfRel());
    }

    @GetMapping("/address/city/{id}")
    public CollectionModel<EntityModel<AddressEntity>> findAddressByCityId(@PathVariable("id") Integer id) {
        List<EntityModel<AddressEntity>> addresses = repository.findByCityId(id).stream()
                .map(address -> EntityModel.of(address,
                        linkTo(methodOn(AddressController.class)
                                .findAddressById(address.getAddressId()))
                                .withSelfRel()))
                .toList();
        return CollectionModel.of(addresses,
                linkTo(methodOn(AddressController.class)
                        .findAddressByCityId(id))
                        .withSelfRel());
    }

    @GetMapping("/address/post/{code}")
    public EntityModel<AddressEntity> findAddressByPostalCode(@PathVariable("code") String code) {
        AddressEntity address = repository.findByPostalCode(code)
                .orElseThrow(() -> new AddressNotFoundException(code));
        return EntityModel.of(address,
                linkTo(methodOn(AddressController.class)
                        .findAllAddresses())
                        .withRel("allAddresses"),
                linkTo(methodOn(AddressController.class)
                        .findAddressByPostalCode(code))
                        .withSelfRel());
    }

    @PostMapping("/address")
    public EntityModel<AddressEntity> addAddress(@RequestBody AddressEntity address) throws ValidationException {
        if(address.getAddressId() == null && address.getAddress() != null && address.getDistrict() != null && address.getCityId() != null && address.getPostalCode() != null && address.getLastUpdate() != null) {
            return EntityModel.of(address,
                    linkTo(methodOn(AddressController.class)
                            .findAllAddresses())
                            .withRel("allAddresses"),
                    linkTo(methodOn(AddressController.class)
                            .findAddressById(address.getAddressId()))
                            .withSelfRel());
        } else {
            throw new ValidationException("Address Cannot Be Created");
        }
    }

    @PutMapping("/address")
    public ResponseEntity<AddressEntity> updateAddress(@RequestBody AddressEntity address) {
        if(repository.findById(address.getAddressId()).isPresent()) {
            return new ResponseEntity<>(repository.save(address), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(address, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}