package com.openclassrooms.medhead;

import com.openclassrooms.medhead.exception.HospitalNotFoundException;
import com.openclassrooms.medhead.client.DistanceClient;
import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.service.DistanceService;

import org.springframework.test.context.TestPropertySource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Collections;

@TestPropertySource(properties = {
	    "map.api.name=googlemap"
})

@TestPropertySource(properties = {
	    "map.api.key=XXXXXXXXXXXXXXXXXXXXXXXX"
})


@ExtendWith(MockitoExtension.class)
public class DistanceServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private DistanceClient distanceClient;

    @InjectMocks
    private DistanceService distanceService;

    @Test
    void testGetNearestAvailableHospitalThrowsException() {
        // Mock de la méthode findAll du repository pour retourner une liste vide
        when(hospitalRepository.findAll()).thenReturn(Collections.emptyList());

        // Vérification que l'exception est levée
        assertThrows(HospitalNotFoundException.class, () -> {
            distanceService.getNearestAvailableHospital(42.8781, -87.6298, "General");
        });
    }

    @Test
    void testGetNearestAvailableHospitalReturnsHospital() {
        // Configuration des objets Hospital
        Hospital hospital1 = new Hospital();
        hospital1.setId(1L);
        hospital1.setLatitude(40.7128);
        hospital1.setLongitude(-74.0060);
        hospital1.setAvailableBeds(5);
        hospital1.setSpecialization("General");

        // Mock de la méthode findAll du repository
        List<Hospital> hospitals = List.of(hospital1);
        when(hospitalRepository.findAll()).thenReturn(hospitals);

        // Mock de la méthode getDistance du client
        when(distanceClient.getDistance(42.8781, -87.6298, 40.7128, -74.0060)).thenReturn(100);
        
        // Attach the mocked distanceClient to the distanceService
        distanceService.setDistanceClient(distanceClient);

        // Appel de la méthode et vérification que le bon hôpital est retourné
        Hospital result = distanceService.getNearestAvailableHospital(42.8781, -87.6298, "General").orElse(null);

        assertEquals(hospital1.getId(), result.getId());
        assertEquals(hospital1.getLatitude(), result.getLatitude());
        assertEquals(hospital1.getLongitude(), result.getLongitude());
    }
}
