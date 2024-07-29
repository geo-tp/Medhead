package com.openclassrooms.medhead.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.adapters.JsonAdapter;

import java.util.Optional;


@Service
public class DistanceService {

    @Value("${api.key}")
    private String apiKey;
    
    @Autowired
    private HospitalRepository hospitalRepository;
    
    private final JsonAdapter jsonAdapter;

    private final RestTemplate restTemplate;
    

    public DistanceService(JsonAdapter jsonAdapter) {
        this.restTemplate = new RestTemplate();
        this.jsonAdapter = jsonAdapter;
    }
    
    
    public Optional<Hospital> getNearestAvailableHospital(double latitude, double longitude) {
        Iterable<Hospital> hospitals = hospitalRepository.findAll();
        Hospital nearestHospital = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Hospital hospital : hospitals) {
            if (hospital.getAvailableBeds() > 0) {
                int distance = calculateDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
                if (distance != -1 && distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestHospital = hospital;
                }
            }
        }

        return Optional.ofNullable(nearestHospital);
    }

    
    private int calculateDistance(double originLat, double originLng, double destLat, double destLng) {
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json")
                .queryParam("origins", originLat + "," + originLng)
                .queryParam("destinations", destLat + "," + destLng)
                .queryParam("key", apiKey)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        return this.jsonAdapter.extractDistanceFromResponse(response);
    }

}
