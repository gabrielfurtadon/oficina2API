package com.oficina.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oficina.domain.Workshop;
import com.oficina.domain.repository.WorkshopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class WorkshopControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WorkshopRepository workshopRepository;

    private Workshop workshop;

    @BeforeEach
    void setUp() {
        workshopRepository.deleteAll();
        workshop = new Workshop();
        workshop.setTitulo("Workshop Java Avançado");
        workshop.setData("2025-04-10");
        workshop.setDuracao("5 horas");
        workshop.setNumeroMaxParticipantes(50);
        workshopRepository.save(workshop);
    }

    @Test
    void deveCriarWorkshop() throws Exception {
        Workshop novoWorkshop = new Workshop();
        novoWorkshop.setTitulo("Workshop Spring Boot");
        novoWorkshop.setData("2025-05-15");
        novoWorkshop.setDuracao("3 horas");
        novoWorkshop.setNumeroMaxParticipantes(30);

        mockMvc.perform(post("/workshops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoWorkshop)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveListarWorkshops() throws Exception {
        mockMvc.perform(get("/workshops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Workshop Java Avançado"));
    }

    @Test
    void deveExcluirWorkshop() throws Exception {
        mockMvc.perform(delete("/workshops/{id}", workshop.getId()))
                .andExpect(status().isNoContent());
    }
}
