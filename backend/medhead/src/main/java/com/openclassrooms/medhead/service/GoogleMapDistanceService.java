package com.openclassrooms.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.client.GoogleMapDistanceClient;
import com.openclassrooms.medhead.model.Hospital;

import java.util.Optional;


@Service
public class GoogleMapDistanceService {

    @Value("${map.key}")
    private String apiKey;
    
    @Autowired
    private HospitalRepository hospitalRepository;
    
    private final GoogleMapDistanceClient distanceClient;
    
    public GoogleMapDistanceService() {
        this.distanceClient = new GoogleMapDistanceClient();

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
