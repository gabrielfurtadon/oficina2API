package com.oficina.unit;

import com.oficina.api.controller.testController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(testController.class)
public class ControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void helloEndpointReturnsHelloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/test/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello world"));
    }
}


