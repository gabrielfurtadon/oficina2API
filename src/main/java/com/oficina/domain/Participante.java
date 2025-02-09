package com.oficina.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Participante {
    @Id
    private UUID id;
    private String name;
    private String ra;
    @JsonIgnore
    @ManyToMany(mappedBy = "participantes")
    private List<Workshop> workshops = new ArrayList<>();
}
