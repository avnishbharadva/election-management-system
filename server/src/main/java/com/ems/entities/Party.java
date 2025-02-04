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

    @Lob
    private Byte[] partySymbol;

    private String leaderName;

    private String partyEmail;

    private String founderName;

    private int foundedYear;

    private String partyIdeology;

    private String partyHeadquarter;

    private String partyWebsite;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates; // One-to-Many mapping with Candidate
}
