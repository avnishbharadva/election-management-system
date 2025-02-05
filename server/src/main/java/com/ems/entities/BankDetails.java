package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BankDetails {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "bank_details_id", sequenceName = "bank_details_sequence", allocationSize = 1, initialValue = 10)

    private Long bankDetailsId;

    private String bankName;

    private String bankAddress;

    @JsonBackReference("candidate-bank")
    @OneToMany(mappedBy = "bankDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Candidate> candidates;
}
