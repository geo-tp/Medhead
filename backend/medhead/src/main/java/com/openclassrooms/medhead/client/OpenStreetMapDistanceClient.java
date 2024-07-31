package com.openclassrooms.medhead.client;

import org.springframework.beans.factory.annotation.Value;

import com.openclassrooms.medhead.adapter.OpenStreetMapJsonAdapter;
import com.openclassrooms.medhead.adapter.JsonAdapter;

public class OpenStreetMapDistanceClient implements DistanceClient {
	
	
	// Placeholder pour valider l'implémentation de plusieurs API
	
	
    @Value("${map.key}")
    private String apiKey;

    private final JsonAdapter jsonAdapter;

    public OpenStreetMapDistanceClient() {
        this.jsonAdapter = new OpenStreetMapJsonAdapter();
    }

    @Override
    public int getDistance(double originLat, double originLng, double destLat, double destLng) {
        String response = "";
        return jsonAdapter.extractDistanceFromResponse(response);
    }
}
