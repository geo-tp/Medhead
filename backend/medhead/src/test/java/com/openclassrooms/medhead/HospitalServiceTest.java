package com.openclassrooms.medhead;

import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.repository.HospitalRepository;
import com.openclassrooms.medhead.service.HospitalService;

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
public class HospitalServiceTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

    @Test
    void testGetHospital() {
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        when(hospitalRepository.findById(1L)).thenReturn(Optional.of(hospital));

        Optional<Hospital> result = hospitalService.getHospital(1L);
        assertEquals(hospital, result.orElse(null));
    }

    @Test
    void testGetHospitals() {
        Iterable<Hospital> hospitals = List.of(new Hospital(), new Hospital());
        when(hospitalRepository.findAll()).thenReturn(hospitals);

        Iterable<Hospital> result = hospitalService.getHospitals();
        assertEquals(hospitals, result);
    }
}
