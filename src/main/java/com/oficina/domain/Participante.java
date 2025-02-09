package com.oficina.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Participante {
    @Id
    private UUID id;
    private String nome;
    private String ra;
}
