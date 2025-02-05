package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Party {
    @Id
    @GeneratedValue
    private Long partyId;

    private String partyName;

    private String partyAbbreviation;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates;
}
