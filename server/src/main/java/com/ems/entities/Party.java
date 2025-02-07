package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] partySymbol;

    private int partyFoundationYear;

    private String partyWebSite;

    private String headQuarters;

    private String founderName;

    @OneToMany(mappedBy = "party", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Candidate> candidates;
}
