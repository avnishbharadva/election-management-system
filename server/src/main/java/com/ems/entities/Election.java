package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
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
    private LocalDate electionDate;

    private String electionState;

    private int totalSeats;

}
