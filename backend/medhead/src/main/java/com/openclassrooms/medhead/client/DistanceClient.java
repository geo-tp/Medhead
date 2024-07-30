package com.openclassrooms.medhead.client;

public interface DistanceClient {
    int getDistance(double originLat, double originLng, double destLat, double destLng);
}