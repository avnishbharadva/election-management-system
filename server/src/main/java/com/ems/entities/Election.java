package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Election {

    @Id
    @GeneratedValue
    private Long electionId;

    private String electionName;

    private String electionType;

    @Temporal(TemporalType.DATE)
    private Date electionDate;

    private String electionState;

    private Integer totalSeats;

    @JsonManagedReference
    @OneToMany(mappedBy = "election", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates;
}
