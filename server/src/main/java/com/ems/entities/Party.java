package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

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
    private Set<Voter> voters;
}
