package com.sparta.springrestapi.repositories;

import com.sparta.springrestapi.entities.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<ActorEntity, Integer> {

    List<ActorEntity> findByLastUpdateBefore(Timestamp lastUpdate);
    List<ActorEntity> findByLastUpdateAfter(Timestamp lastUpdate);

}