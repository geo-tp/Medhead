package com.openclassrooms.medhead.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.service.HospitalService;
import com.openclassrooms.medhead.service.DistanceService;
import com.openclassrooms.medhead.client.DistanceClient;
import com.openclassrooms.medhead.client.GoogleMapDistanceClient;

@RestController
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private DistanceService distanceService;
    
    public HospitalController() {
    	DistanceClient distanceClient = new GoogleMapDistanceClient();
        this.distanceService = new DistanceService(distanceClient);
    }
    
    @GetMapping("/hospitals")
    public Iterable<Hospital> getHospitals() {
        return hospitalService.getHospitals();
    }

    @GetMapping("/hospital/{id}")
    public Optional<Hospital> getHospital(@PathVariable Long id) {
        return hospitalService.getHospital(id);
    }

    @GetMapping("/hospital/nearest")
    public Optional<Hospital> getNearestAvailableHospital(@RequestParam double lat, @RequestParam double lng) {
        return distanceService.getNearestAvailableHospital(lat, lng);
    }

}