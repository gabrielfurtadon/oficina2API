package com.oficina.domain.repository;

import com.oficina.domain.Participante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    Participante getByRa(String ra);
}
