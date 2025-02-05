package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
public class Candidate extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
    @SequenceGenerator(name = "candidate_seq", sequenceName = "candidate_sequence", allocationSize = 1, initialValue = 1000)
    private Long candidateId;


    @Embedded
    private CandidateName candidateName;

    private String candidateSSN;

    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] candidateImage;

    @Enumerated(EnumType.STRING)
    private MaritialStatus maritialStatus;

    private int noOfChildren;

    private String spouseName;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "party_id")
    private Party party;

    private String stateName;

    private String candidateEmail;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<CandidateAddress> address;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;

    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] candidateSignature;



}
