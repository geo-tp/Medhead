package com.openclassrooms.medhead.adapters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleMapAdapter implements JsonAdapter {

    private final ObjectMapper objectMapper;

    public GoogleMapAdapter() {
        this.objectMapper = new ObjectMapper();
    }

    public int extractDistanceFromResponse(String response) {
        try {
            JsonNode root = objectMapper.readTree(response);
            JsonNode rows = root.path("rows").get(0);
            JsonNode elements = rows.path("elements").get(0);
            JsonNode distance = elements.path("distance");
            return distance.path("value").asInt();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
