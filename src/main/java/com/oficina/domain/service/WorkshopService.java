package com.oficina.domain.service;

import com.oficina.domain.Participante;
import com.oficina.domain.Workshop;
import com.oficina.domain.repository.ParticipanteRepository;
import com.oficina.domain.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkshopService {

    @Autowired
    private  WorkshopRepository workshopRepository;
    @Autowired
    private  ParticipanteRepository participanteRepository;


    public Workshop criarWorkshop(Workshop workshop) {
        return workshopRepository.save(workshop);
    }

    public List<Workshop> listarWorkshops() {
        return workshopRepository.findAll();
    }

    public Workshop buscarWorkshopPorId(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Workshop não encontrado com id: " + id));
    }

    public Workshop atualizarParticipantes(Long workshopId, List<String> raList) {
        Workshop workshop = buscarWorkshopPorId(workshopId);

        if (raList.size() > workshop.getNumeroMaxParticipantes()) {
            throw new IllegalStateException("Número de participantes excede o limite máximo permitido.");
        }

        List<Participante> novosParticipantes = new ArrayList<>();
        for (String ra : raList) {
            Participante participante = participanteRepository.getByRa(ra);
            if (participante == null) {
                throw new IllegalArgumentException("Participante com RA " + ra + " não encontrado.");
            }
            novosParticipantes.add(participante);
        }

        List<Participante> participantesAtuais = new ArrayList<>(workshop.getParticipantes());
        for (Participante participante : participantesAtuais) {
            if (!novosParticipantes.contains(participante)) {
                workshop.getParticipantes().remove(participante);
                participante.getWorkshops().remove(workshop);
                participanteRepository.save(participante);
            }
        }

        for (Participante participante : novosParticipantes) {
            if (!workshop.getParticipantes().contains(participante)) {
                workshop.getParticipantes().add(participante);
                participante.getWorkshops().add(workshop);
                participanteRepository.save(participante);
            }
        }

        return workshopRepository.save(workshop);
    }

    public Workshop atualizarWorkshop(Long id, Workshop workshopAtualizado) {
        Workshop workshopExistente = buscarWorkshopPorId(id);
        if(workshopExistente == null) {
            throw new IllegalArgumentException("Workshop não encontrado");
        }
        workshopExistente.setTitulo(workshopAtualizado.getTitulo());
        workshopExistente.setDescricao(workshopAtualizado.getDescricao());
        workshopExistente.setData(workshopAtualizado.getData());
        workshopExistente.setDuracao(workshopAtualizado.getDuracao());
        workshopExistente.setNumeroMaxParticipantes(workshopAtualizado.getNumeroMaxParticipantes());
        workshopExistente.setTipoEvento(workshopAtualizado.getTipoEvento());
        return workshopRepository.save(workshopExistente);
    }

    public void excluirWorkshop(Long id) {
        Workshop workshop = buscarWorkshopPorId(id);
        if(workshop == null) {
            throw new IllegalArgumentException("Workshop não encontrado");
        }
        workshopRepository.delete(workshop);
    }

    public Workshop removerParticipante(Long workshopId, String ra) {
        Workshop workshop = buscarWorkshopPorId(workshopId);
        if(workshop == null) {
            throw new IllegalArgumentException("Workshop não encontrado");
        }
        Participante participante = participanteRepository.getByRa(ra);
        if (participante == null) {
            throw new IllegalArgumentException("Não existe participante com esse RA cadastrado");
        }
        if (!workshop.getParticipantes().contains(participante)) {
            throw new IllegalArgumentException("Participante não está cadastrado no workshop.");
        }

        workshop.getParticipantes().remove(participante);
        participante.getWorkshops().remove(workshop);

        participanteRepository.save(participante);
        return workshopRepository.save(workshop);
    }
}
