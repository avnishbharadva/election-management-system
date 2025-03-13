package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @OneToMany(mappedBy = "voterStatus")
    private List<Voter> voters;
}
