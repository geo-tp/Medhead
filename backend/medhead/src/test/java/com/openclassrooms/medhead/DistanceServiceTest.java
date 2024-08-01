package com.openclassrooms.medhead;

import com.openclassrooms.medhead.client.DistanceClient;
import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.service.DistanceService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private DistanceClient distanceClient;

    @InjectMocks
    private DistanceService distanceService;

    @Test
    void testGetNearestAvailableHospital() {
        // Configuration des objets Hospital
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setLatitude(40.7128);
        hospital1.setLongitude(-74.0060);
        hospital1.setAvailableBeds(5);

        Hospital hospital2 = new Hospital();
        hospital2.setId(2L);
        hospital2.setLatitude(34.0522);
        hospital2.setLongitude(-118.2437);
        hospital2.setAvailableBeds(3);

        // Mock de la méthode findAll du repository
        List<Hospital> hospitals = List.of(hospital1, hospital2);
        when(hospitalRepository.findAll()).thenReturn(hospitals);

        // Mock de la méthode getDistance du client
        // when(distanceClient.getDistance(41.8781, -87.6298, 40.7128, -74.0060)).thenReturn(100);
        // when(distanceClient.getDistance(41.8781, -87.6298, 34.0522, -118.2437)).thenReturn(200);
        
        // Appel de la méthode
        Optional<Hospital> result = distanceService.getNearestAvailableHospital(42.8781, -87.6298);
        
        assertEquals(null, result.orElse(null));
    }
}
