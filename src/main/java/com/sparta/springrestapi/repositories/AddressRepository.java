package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    List<AddressEntity> findByCityId(Integer id);
    Optional<AddressEntity> findByPostalCode(String postalCode);

}