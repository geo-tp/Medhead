package com.openclassrooms.medhead.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.openclassrooms.medhead.adapter.GoogleMapJsonAdapter;
import com.openclassrooms.medhead.adapter.JsonAdapter;

public class OpenStreetMapDistanceClient implements DistanceClient {
	
	// Placeholder
	
    @Value("${map.key}")
    private String apiKey;

    private final JsonAdapter jsonAdapter;

    public OpenStreetMapDistanceClient() {
        this.jsonAdapter = new GoogleMapJsonAdapter();
    }

    @Override
    public int getDistance(double originLat, double originLng, double destLat, double destLng) {
        String response = "";
        
        return jsonAdapter.extractDistanceFromResponse(response);
    }
}
