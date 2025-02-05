package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CandidateAddress {

    @Id
    @GeneratedValue
    private Long addressId;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JsonBackReference("candidate-address")
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String street;

    private String city;

    private int zipcode;
}
