package com.openclassrooms.medhead.config;

import com.openclassrooms.medhead.adapters.GoogleMapAdapter;
import com.openclassrooms.medhead.adapters.JsonAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonAdapterConfig {

    @Value("${map.api}") // Nom de l'api map utilisée
    private String jsonAdapter;

    @Autowired
    private GoogleMapAdapter googleMapAdapter;

    @Bean
    public JsonAdapter getJsonAdapter() {
        if ("googleMap".equalsIgnoreCase(jsonAdapter)) {
            return googleMapAdapter;
        }
        // Autres adaptateurs ici
        throw new IllegalArgumentException("Invalid JSON adapter specified: " + jsonAdapter);
    }
}
