package com.oficina.domain.service;

import com.oficina.domain.Participante;
import com.oficina.domain.Workshop;
import com.oficina.domain.repository.ParticipanteRepository;
import com.oficina.domain.repository.WorkshopRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkshopServiceTest {

    @InjectMocks
    private WorkshopService workshopService;

    @Mock
    private WorkshopRepository workshopRepository;

    @Mock
    private ParticipanteRepository participanteRepository;

    private Workshop workshop;
    private Participante participante;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        workshop = new Workshop();
        workshop.setId(1L);
        workshop.setTitulo("Workshop de Java");
        workshop.setData("2025-03-20");
        workshop.setDuracao("4 horas");
        workshop.setNumeroMaxParticipantes(2);

        participante = new Participante();
        participante.setId(UUID.randomUUID());
        participante.setRa("RA123");
        participante.setName("João Silva");
    }

    @Test
    void deveCriarWorkshop() {
        when(workshopRepository.save(any(Workshop.class))).thenReturn(workshop);

        Workshop novoWorkshop = workshopService.criarWorkshop(workshop);
        assertNotNull(novoWorkshop);
        assertEquals("Workshop de Java", novoWorkshop.getTitulo());
    }

    @Test
    void deveBuscarWorkshopPorId() {
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));

        Workshop foundWorkshop = workshopService.buscarWorkshopPorId(1L);
        assertNotNull(foundWorkshop);
        assertEquals(1L, foundWorkshop.getId());
    }

    @Test
    void deveAdicionarParticipanteNoWorkshop() {
        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(participanteRepository.getByRa("RA123")).thenReturn(participante);

        when(workshopRepository.save(any(Workshop.class))).thenReturn(workshop);
        List<String> participantes = new ArrayList<>();
        participantes.add("RA123");
        Workshop updatedWorkshop = workshopService.atualizarParticipantes(1L, participantes);

        assertNotNull(updatedWorkshop);
        assertEquals(1, updatedWorkshop.getParticipantes().size());
    }


    @Test
    void deveRemoverParticipanteDoWorkshop() {
        workshop.getParticipantes().add(participante);

        when(workshopRepository.findById(1L)).thenReturn(Optional.of(workshop));
        when(participanteRepository.getByRa("RA123")).thenReturn(participante);

        when(workshopRepository.save(any(Workshop.class))).thenReturn(workshop);

        Workshop updatedWorkshop = workshopService.removerParticipante(1L, "RA123");

        assertNotNull(updatedWorkshop);
        assertEquals(0, updatedWorkshop.getParticipantes().size());
    }
}
