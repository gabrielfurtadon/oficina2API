package com.oficina.domain.service;

import com.oficina.domain.Participante;
import com.oficina.domain.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipanteService {

    @Autowired
    ParticipanteRepository repository;

    public Participante cadastrarParticipante(Participante participante) {
        if (repository.getByRa(participante.getRa()) != null) {
            throw new IllegalArgumentException("Já existe um participante com o RA: " + participante.getRa());
        }
        return repository.save(participante);
    }

    public List<Participante> listarParticipantes() {
        return repository.findAll();
    }

    public Participante buscarParticipantePorRa(String ra) {
        Participante participante = repository.getByRa(ra);
        if (participante == null) {
            throw new UsernameNotFoundException("Participante não encontrado com RA: " + ra);
        }
        return participante;
    }

    public Participante atualizarParticipante(Participante participante) {

        Participante participanteExistente = buscarParticipantePorRa(participante.getRa());

        participanteExistente.setNome(participante.getNome());
        participanteExistente.setRa(participante.getRa());

        return repository.save(participanteExistente);
    }

    public void excluirParticipante(String ra) {
        Participante participanteExistente = buscarParticipantePorRa(ra);
        repository.delete(participanteExistente);
    }



}
