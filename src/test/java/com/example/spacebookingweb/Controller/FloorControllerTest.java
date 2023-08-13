package com.example.spacebookingweb.Controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FloorController.class)
class FloorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Test
    void getFloorList() {
    }

    @Test
    void getFloorById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/floor/1");
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        assertEquals("", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getFloorReservationsByFloorIdAndDate() {
    }
}