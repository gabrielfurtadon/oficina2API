package com.oficina.domain;

import com.oficina.domain.enums.TiposEvento;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class Workshop {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String descricao;
    private String data;
    private String duracao;
    private int numeroMaxParticipantes;
    @Enumerated(EnumType.STRING)
    private TiposEvento tipoEvento;

    @ManyToMany
    @JoinTable(
            name = "workshop_participante",
            joinColumns = @JoinColumn(name = "workshop_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    private List<Participante> participantes = new ArrayList<>();

}
