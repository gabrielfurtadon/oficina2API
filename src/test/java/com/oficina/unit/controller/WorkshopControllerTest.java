package com.oficina.unit.controller;

import com.oficina.api.controller.WorkshopController;
import com.oficina.domain.Workshop;
import com.oficina.domain.service.WorkshopService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WorkshopControllerTest {

    @InjectMocks
    private WorkshopController workshopController;

    @Mock
    private WorkshopService workshopService;

    public WorkshopControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarWorkshop() {
        Workshop workshop = new Workshop();
        workshop.setTitulo("Workshop Angular");

        when(workshopService.criarWorkshop(any())).thenReturn(workshop);

        ResponseEntity<Workshop> response = workshopController.criarWorkshop(workshop);

        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Workshop Angular", response.getBody().getTitulo());
    }

    @Test
    void deveBuscarWorkshopPorId() {
        Workshop workshop = new Workshop();
        workshop.setId(1L);
        workshop.setTitulo("Workshop Spring");

        when(workshopService.buscarWorkshopPorId(1L)).thenReturn(workshop);

        ResponseEntity<Workshop> response = workshopController.buscarWorkshop(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void deveExcluirWorkshop() {
        doNothing().when(workshopService).excluirWorkshop(1L);

        ResponseEntity<?> response = workshopController.excluirWorkshop(1L);

        assertEquals(204, response.getStatusCodeValue());
    }
}
