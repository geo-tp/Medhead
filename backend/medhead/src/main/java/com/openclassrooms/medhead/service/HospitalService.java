package com.openclassrooms.medhead.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.repository.HospitalRepository;

import lombok.Data;

@Data
@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    public Optional<Hospital> getHospital(final Long id) {
        return hospitalRepository.findById(id);
    }

    public Iterable<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }
    
}
