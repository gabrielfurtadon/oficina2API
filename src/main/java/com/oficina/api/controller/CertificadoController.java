package com.oficina.api.controller;

import com.oficina.domain.service.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    @Autowired
    private CertificadoService certificateService;


    @GetMapping("/workshops/{workshopId}/zip")
    public ResponseEntity<?> generateCertificatesZip(@PathVariable Long workshopId) {
        try {
            byte[] zipBytes = certificateService.generateCertificatesZip(workshopId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=certificados_workshop_" + workshopId + ".zip");
            return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao gerar os certificados: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
