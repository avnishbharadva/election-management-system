package com.ems.entities;

import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritialStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@Entity
public class Candidate extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidate_seq")
    @SequenceGenerator(name = "candidate_seq", sequenceName = "candidate_sequence", allocationSize = 1, initialValue = 2000)
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
    @JsonBackReference("party-candidate")
    @JoinColumn(name = "party_id")
    private Party party;

    private String stateName;
    private String candidateEmail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "residential_address_id", referencedColumnName = "addressId")
    private CandidateAddress residentialAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mailing_address_id", referencedColumnName = "addressId")
    private CandidateAddress mailingAddress;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "election_id")
    private Election election;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "bank_details_id")
    private BankDetails bankDetails;

    @Lob
    @Column(columnDefinition = "BLOB")
    private Byte[] candidateSignature;
}
