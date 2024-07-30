package com.openclassrooms.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.client.DistanceClient;
import com.openclassrooms.medhead.model.Hospital;

import java.util.Optional;


@Service
public class DistanceService {

    @Autowired
    private HospitalRepository hospitalRepository;
    
    private final DistanceClient distanceClient;
    
    public DistanceService(DistanceClient distanceClient) {
        this.distanceClient = distanceClient;
    }
    
    public Optional<Hospital> getNearestAvailableHospital(double latitude, double longitude) {
        Iterable<Hospital> hospitals = hospitalRepository.findAll();
        Hospital nearestHospital = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Hospital hospital : hospitals) {
            if (hospital.getAvailableBeds() > 0) {
                int distance = distanceClient.getDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
                if (distance != -1 && distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestHospital = hospital;
                }
            }
        }

        return Optional.ofNullable(nearestHospital);
    }
}
