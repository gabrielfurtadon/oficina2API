package com.oficina.api.controller;

import com.oficina.domain.Workshop;
import com.oficina.domain.service.WorkshopService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/workshops")
public class WorkshopController {

    private final WorkshopService workshopService;

    public WorkshopController(WorkshopService workshopService) {
        this.workshopService = workshopService;
    }

    @PostMapping
    public ResponseEntity<Workshop> criarWorkshop(@RequestBody Workshop workshop) {
        Workshop novoWorkshop = workshopService.criarWorkshop(workshop);
        return new ResponseEntity<>(novoWorkshop, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<Workshop>> listarWorkshops() {
        List<Workshop> workshops = workshopService.listarWorkshops();
        return new ResponseEntity<>(workshops, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Workshop> buscarWorkshop(@PathVariable Long id) {
        Workshop workshop = workshopService.buscarWorkshopPorId(id);
        return new ResponseEntity<>(workshop, HttpStatus.OK);
    }

    @PutMapping("/participantes/{id}")
    public ResponseEntity<?> atualizarParticipantes(@PathVariable Long id,
                                                    @RequestBody Map<String, List<String>> body) {
        try {
            List<String> raList = body.get("participantes");
            Workshop workshopAtualizado = workshopService.atualizarParticipantes(id, raList);
            return new ResponseEntity<>(workshopAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarWorkshop(@PathVariable Long id, @RequestBody Workshop workshop) {
        try {
            Workshop workshopAtualizado = workshopService.atualizarWorkshop(id, workshop);
            return new ResponseEntity<>(workshopAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirWorkshop(@PathVariable Long id) {
        try {
            workshopService.excluirWorkshop(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}/participantes/{ra}")
    public ResponseEntity<?> removerParticipante(@PathVariable Long id, @PathVariable String ra) {
        try {
            Workshop workshopAtualizado = workshopService.removerParticipante(id, ra);
            return new ResponseEntity<>(workshopAtualizado, HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }
}

