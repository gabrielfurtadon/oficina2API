package com.oficina.unit.service;

import com.oficina.domain.Participante;
import com.oficina.domain.Workshop;
import com.oficina.domain.repository.WorkshopRepository;
import com.oficina.domain.service.CertificadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CertificateServiceTest {

    @InjectMocks
    private CertificadoService certificateService;

    @Mock
    private WorkshopRepository workshopRepository;

    private Workshop workshop;
    private Participante participante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        workshop = new Workshop();
        workshop.setId(1L);
        workshop.setTitulo("Workshop Spring Boot");
        workshop.setData("2025-05-10");
        workshop.setDuracao("3 horas");

        participante = new Participante();
        participante.setRa("RA456");
        participante.setName("Maria Souza");

        workshop.setParticipantes(Collections.singletonList(participante));
    }

    @Test
    void deveGerarCertificadoZip() throws IOException {
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));

        byte[] zipBytes = certificateService.generateCertificatesZip(1L);
        assertNotNull(zipBytes);
        assertTrue(zipBytes.length > 0);
    }
}
