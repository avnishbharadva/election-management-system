package com.ems.entities;

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
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    private String street;

    private String city;

    private int zipcode;
}
