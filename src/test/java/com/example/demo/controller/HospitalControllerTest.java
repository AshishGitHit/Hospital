package com.example.demo.controller;

import static org.mockito.Mockito.when;

import com.example.demo.service.HospitalServiceImpl;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HospitalController.class})
@ExtendWith(SpringExtension.class)
class HospitalControllerTest {
    @Autowired
    private HospitalController hospitalController;

    @MockBean
    private HospitalServiceImpl hospitalServiceImpl;

    @Test
    void shouldGetAllBeds() throws Exception {
        when(this.hospitalServiceImpl.getAllBeds()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospital/bed");
        MockMvcBuilders.standaloneSetup(this.hospitalController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void shouldGetAllPatients() throws Exception {
        when(this.hospitalServiceImpl.getAllPatients()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/hospital/patient");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(this.hospitalController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

//    @Test
//    void testGetAllBeds2() throws Exception {
//        when(this.hospitalServiceImpl.getAllBeds()).thenReturn(new ArrayList<>());
//        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/hospital/bed");
//        getResult.characterEncoding("Encoding");
//        MockMvcBuilders.standaloneSetup(this.hospitalController)
//                .build()
//                .perform(getResult)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("[]"));
//    }


//    @Test
//    void testGetAllPatients() throws Exception {
//        when(this.hospitalServiceImpl.getAllPatients()).thenReturn(new ArrayList<>());
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hospital/patient");
//        MockMvcBuilders.standaloneSetup(this.hospitalController)
//                .build()
//                .perform(requestBuilder)
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content().string("[]"));
//    }

}

