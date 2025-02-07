package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Party extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long partyId;

    @Column(nullable = false)
    private String partyName;

    @Column(nullable = false)
    private String abbreviation;

    @OneToMany(mappedBy = "party")
    @JsonManagedReference
    private List<Voter> voters;
}
