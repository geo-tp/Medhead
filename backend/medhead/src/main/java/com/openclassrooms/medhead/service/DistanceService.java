package com.openclassrooms.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    
    private final DistanceClient distanceClient;
    
    public DistanceService() {   
    	apiName = "googlemap";
        switch (apiName) {
	        case "googlemap":
	            this.distanceClient = new GoogleMapDistanceClient();
	            break;
	        case "openstreetmap":
	            this.distanceClient = new OpenStreetMapDistanceClient();
	            break;
	        default:
	            throw new IllegalArgumentException("Unknown map API: " + apiName);
        }
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
        
        if (nearestHospital == null) {
            throw new HospitalNotFoundException();
        }

        return Optional.ofNullable(nearestHospital);
    }
}
