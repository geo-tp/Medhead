package com.openclassrooms.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.client.DistanceClient;
import com.openclassrooms.medhead.client.GoogleMapDistanceClient;
import com.openclassrooms.medhead.client.OpenStreetMapDistanceClient;
import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.exception.HospitalNotFoundException;

import java.util.Optional;


@Service
public class DistanceService {
	
    @Value("${map.api.name}")
    private String apiName;

    @Autowired
    private HospitalRepository hospitalRepository;
    
    @Autowired
    private GoogleMapDistanceClient googleMapDistanceClient;

    @Autowired
    private OpenStreetMapDistanceClient openStreetMapDistanceClient;
    
    private DistanceClient distanceClient;
    
    public DistanceService() {
    }
    
    @PostConstruct
    private void setDefaultDistanceClient() {
        switch (this.apiName) {
            case "googlemap":
                this.distanceClient = googleMapDistanceClient;
                break;
            case "openstreetmap":
                this.distanceClient = openStreetMapDistanceClient;
                break;
            default:
                throw new IllegalArgumentException("Unknown map API: " + this.apiName);
        }
    }
    
    // Setter for testing purpose
    public void setDistanceClient(DistanceClient distanceClient) {
        this.distanceClient = distanceClient;
    }
    
    public Optional<Hospital> getNearestAvailableHospital(double latitude, double longitude, String specialization) {
        Iterable<Hospital> hospitals = hospitalRepository.findAll();
        Hospital nearestHospital = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Hospital hospital : hospitals) {
            // Filtrer par specialisation et lits disponibles
            if (hospital.getAvailableBeds() > 0 && hospital.getSpecialization().equalsIgnoreCase(specialization)) {
                int distance = distanceClient.getDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
                if (distance != -1 && distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestHospital = hospital;
                }
            }
        }
        
        if (nearestHospital == null) {
            throw new HospitalNotFoundException();
        }

        return Optional.ofNullable(nearestHospital);
    }
}
