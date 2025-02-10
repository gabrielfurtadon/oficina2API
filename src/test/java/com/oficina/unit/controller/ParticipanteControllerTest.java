package com.oficina.unit.controller;

import com.oficina.api.controller.ParticipanteController;
import com.oficina.domain.Participante;
import com.oficina.domain.service.ParticipanteService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipanteControllerTest {

    @InjectMocks
    private ParticipanteController participanteController;

    @Mock
    private ParticipanteService participanteService;

    public ParticipanteControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCadastrarParticipante() {
        Participante participante = new Participante();
        participante.setRa("RA123");
        participante.setName("Carlos Oliveira");
        participante.setId(UUID.randomUUID());

        when(participanteService.cadastrarParticipante(any())).thenReturn(participante);

        ResponseEntity<?> response = participanteController.cadastrarParticipante(participante);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void deveBuscarParticipantePorRa() {
        Participante participante = new Participante();
        participante.setRa("RA123");
        participante.setName("Carlos Oliveira");

        when(participanteService.buscarParticipantePorRa("RA123")).thenReturn(participante);

        ResponseEntity<Participante> response = participanteController.buscarParticipantePorRa("RA123");

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("RA123", response.getBody().getRa());
    }
}
