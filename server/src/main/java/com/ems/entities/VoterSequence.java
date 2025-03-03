package com.ems.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "voter_sequence")
public class VoterSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
