package com.oficina.unit.service;

import com.oficina.domain.Participante;
import com.oficina.domain.repository.ParticipanteRepository;
import com.oficina.domain.service.ParticipanteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipanteServiceTest {

    @InjectMocks
    private ParticipanteService participanteService;

    @Mock
    private ParticipanteRepository participanteRepository;

    private Participante participante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        participante = new Participante();
        participante.setId(UUID.randomUUID());
        participante.setRa("RA123");
        participante.setName("João Silva");
    }

    @Test
    void deveCadastrarParticipante() {
        when(participanteRepository.getByRa("RA123")).thenReturn(null);
        when(participanteRepository.save(any(Participante.class))).thenReturn(participante);

        Participante novoParticipante = participanteService.cadastrarParticipante(participante);

        assertNotNull(novoParticipante);
        assertEquals("RA123", novoParticipante.getRa());
    }

    @Test
    void naoDeveCadastrarParticipanteComRaDuplicado() {
        when(participanteRepository.getByRa("RA123")).thenReturn(participante);

        assertThrows(IllegalArgumentException.class, () -> participanteService.cadastrarParticipante(participante));
    }

    @Test
    void deveBuscarParticipantePorRa() {
        when(participanteRepository.getByRa("RA123")).thenReturn(participante);

        Participante found = participanteService.buscarParticipantePorRa("RA123");

        assertNotNull(found);
        assertEquals("RA123", found.getRa());
    }

    @Test
    void naoDeveEncontrarParticipante() {
        when(participanteRepository.getByRa("RA123")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> participanteService.buscarParticipantePorRa("RA123"));
    }

    @Test
    void deveExcluirParticipante() {
        when(participanteRepository.getByRa("RA123")).thenReturn(participante);

        participanteService.excluirParticipante("RA123");

        verify(participanteRepository, times(1)).delete(participante);
    }
}
