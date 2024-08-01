package com.openclassrooms.medhead;

import com.openclassrooms.medhead.controller.HospitalController;
import com.openclassrooms.medhead.model.Hospital;
import com.openclassrooms.medhead.service.HospitalService;
import com.openclassrooms.medhead.service.DistanceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HospitalController.class)
public class HospitalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HospitalService hospitalService;

    @MockBean
    private DistanceService distanceService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetHospitals() throws Exception {
        mockMvc.perform(get("/hospitals"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetHospital() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setId(1L);
        when(hospitalService.getHospital(1L)).thenReturn(Optional.of(hospital));

        mockMvc.perform(get("/hospital/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetNearestAvailableHospital() throws Exception {
        Hospital hospital = new Hospital();
        hospital.setLatitude(40.7128);
        hospital.setLongitude(-74.0060);
        when(distanceService.getNearestAvailableHospital(41.8781, -87.6298)).thenReturn(Optional.of(hospital));

        mockMvc.perform(get("/hospital/nearest?lat=41.8781&lng=-87.6298"))
                .andExpect(status().isOk());
    }
}
