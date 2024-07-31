package com.openclassrooms.medhead.adapter;
import java.util.Random;


public class OpenStreetMapJsonAdapter implements JsonAdapter {
	
	
	// Placeholder pour valider l'implémentation de plusieurs API


    @Override
    public int extractDistanceFromResponse(String response) {
        Random random = new Random();
        return random.nextInt(10000) + 1;
    }
}

