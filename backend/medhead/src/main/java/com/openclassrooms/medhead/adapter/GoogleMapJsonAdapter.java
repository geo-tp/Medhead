package com.openclassrooms.medhead.adapter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Component;

@Component
public class GoogleMapJsonAdapter implements JsonAdapter {

    private final ObjectMapper objectMapper;

    public GoogleMapJsonAdapter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public int extractDistanceFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode rows = root.path("rows").get(0);
            JsonNode elements = rows.path("elements").get(0);
            JsonNode distance = elements.path("distance");
            return distance.path("value").asInt();
        } catch (Exception e) {
            return -1;
        }
    }
}
