package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoterStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String statusDesc;
}
