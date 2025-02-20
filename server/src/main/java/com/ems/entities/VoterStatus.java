package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class VoterStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String statusDesc;

    @OneToMany(mappedBy = "voterStatus")
    private List<Voter> voters;
}
