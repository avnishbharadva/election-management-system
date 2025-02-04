package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Candidate extends TimeStamp {

    @Id
    @GeneratedValue
    private Long candidateId;

    @Embedded
    private CandidateName candidateName;

    private String candidateSSN;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    private Byte[] candidateImage;

    @Enumerated(EnumType.STRING)
    private MaritialStatus maritialStatus;

    private int noOfChildren;

    private String spouseName;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    private String stateName;

    private String candidateEmail;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> address;

    @ManyToOne
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;

    @Lob
    private Byte[] candidateSignature;



}
