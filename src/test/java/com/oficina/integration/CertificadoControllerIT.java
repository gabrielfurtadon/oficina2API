package com.oficina.integration;

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

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CertificadoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkshopRepository workshopRepository;

    private Workshop workshop;

    @BeforeEach
    void setUp() {
        workshopRepository.deleteAll();
        workshop = new Workshop();
        workshop.setTitulo("Workshop Spring Security");
        workshop.setData("2025-06-20");
        workshop.setDuracao("4 horas");
        workshop.setNumeroMaxParticipantes(20);
        workshop.setParticipantes(Collections.emptyList());
        workshopRepository.save(workshop);
    }

    @Test
    void deveGerarCertificadoZip() throws Exception {
        mockMvc.perform(get("/certificados/workshops/{workshopId}/zip", workshop.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
