package com.oficina.domain.repository;

import com.oficina.domain.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, UUID> {
    Participante getByRa(String ra);
}
