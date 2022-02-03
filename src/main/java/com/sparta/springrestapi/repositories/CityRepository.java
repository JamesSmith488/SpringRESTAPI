package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

    List<CityEntity> findByCountryId(Integer id);

}