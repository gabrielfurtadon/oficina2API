package com.oficina.api.controller;

import com.oficina.domain.Participante;
import com.oficina.domain.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;


    @PostMapping
    public ResponseEntity<?> cadastrarParticipante(@RequestBody Participante participante) {
        try {
            Participante novoParticipante = participanteService.cadastrarParticipante(participante);
            return new ResponseEntity<>(novoParticipante, HttpStatus.CREATED);
        } catch(IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping
    public ResponseEntity<List<Participante>> listarParticipantes() {
        List<Participante> participantes = participanteService.listarParticipantes();
        return new ResponseEntity<>(participantes, HttpStatus.OK);
    }

    @GetMapping("/{ra}")
    public ResponseEntity<Participante> buscarParticipantePorRa(@PathVariable String ra) {
        Participante participante = participanteService.buscarParticipantePorRa(ra);
        return new ResponseEntity<>(participante, HttpStatus.OK);
    }

    @PutMapping("/{ra}")
    public ResponseEntity<Participante> atualizarParticipante(@PathVariable String ra,
                                                              @RequestBody Participante participante) {
        participante.setRa(ra);
        Participante participanteAtualizado = participanteService.atualizarParticipante(participante);
        return new ResponseEntity<>(participanteAtualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{ra}")
    public ResponseEntity<Void> excluirParticipante(@PathVariable String ra) {
        participanteService.excluirParticipante(ra);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
