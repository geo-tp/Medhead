package com.openclassrooms.medhead.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.medhead.model.Hospital;

@Repository
public interface HospitalRepository extends CrudRepository<Hospital, Long> {

}