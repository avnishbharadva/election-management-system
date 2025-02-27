package com.ems.entities;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritialStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.Date;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "candidateCache")
public class Candidate extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
    @SequenceGenerator(name = "candidate_seq", sequenceName = "candidate_sequence", allocationSize = 1, initialValue = 2000)
    private Long candidateId;

    @Embedded
    private CandidateName candidateName;

//    @Column(unique = true)
    @Column(name = "candidatessn")
    private String candidateSSN;

    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String candidateImage;


    @Enumerated(EnumType.STRING)
    private MaritialStatus maritialStatus;

    private int noOfChildren;
    private String spouseName;

    @ManyToOne
    @JsonBackReference("party-candidate")
    @JoinColumn(name = "party_id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Party party;

    private String stateName;
    private String candidateEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "residential_address_id", referencedColumnName = "addressId")
    private CandidateAddress residentialAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "mailing_address_id", referencedColumnName = "addressId")
    private CandidateAddress mailingAddress;

    @ManyToOne
    @JsonBackReference
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;

    private String candidateSignature;

}
