package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Election extends AuditEntity {

    @Id
    @GeneratedValue
    private Long electionId;

    private String electionName;

    private String electionType;

    @Temporal(TemporalType.DATE)
    private LocalDate electionDate;

    private String electionState;

    private int totalSeats;

}
