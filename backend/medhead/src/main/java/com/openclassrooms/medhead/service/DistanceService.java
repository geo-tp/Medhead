package com.openclassrooms.medhead.service;

import com.openclassrooms.medhead.model.Hospital;

import java.util.Optional;

public interface DistanceService {
    Optional<Hospital> getNearestAvailableHospital(double latitude, double longitude);
}
