package com.oficina.unit.controller;

import com.oficina.api.controller.CertificadoController;
import com.oficina.domain.service.CertificadoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CertificadoControllerTest {

    @InjectMocks
    private CertificadoController certificadoController;

    @Mock
    private CertificadoService certificadoService;

    public CertificadoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveGerarZipComCertificados() throws IOException {
        byte[] zipMock = new byte[]{1, 2, 3, 4};

        when(certificadoService.generateCertificatesZip(1L)).thenReturn(zipMock);

        ResponseEntity<?> response = certificadoController.generateCertificatesZip(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(4, ((byte[]) response.getBody()).length);
    }
}
