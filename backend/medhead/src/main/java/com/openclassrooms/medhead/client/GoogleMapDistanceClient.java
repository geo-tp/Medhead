package com.openclassrooms.medhead.client;

import com.openclassrooms.medhead.adapter.GoogleMapJsonAdapter;
import com.openclassrooms.medhead.adapter.JsonAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GoogleMapDistanceClient implements DistanceClient {
	
    @Value("${map.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final JsonAdapter jsonAdapter;

    public GoogleMapDistanceClient() {
        this.restTemplate = new RestTemplate();
        this.jsonAdapter = new GoogleMapJsonAdapter();
    }

    @Override
    public int getDistance(double originLat, double originLng, double destLat, double destLng) {
        String url = buildUrl(originLat, originLng, destLat, destLng);
        String response = restTemplate.getForObject(url, String.class);
        
        return jsonAdapter.extractDistanceFromResponse(response);
    }

    private String buildUrl(double originLat, double originLng, double destLat, double destLng) {
        return UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/distancematrix/json")
                .queryParam("origins", originLat + "," + originLng)
                .queryParam("destinations", destLat + "," + destLng)
                .queryParam("key", apiKey)
                .toUriString();
    }
}


