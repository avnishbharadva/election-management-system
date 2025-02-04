package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BankDetails {

    @Id
    @GeneratedValue
    private Long bankDetailsId;

    private String bankName;

    private String bankAddress;

    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates;
}
