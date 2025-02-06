package com.ems.entities;

import com.ems.entities.constants.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Voter extends TimeStamp {
    @Id
    @Column(unique = true, nullable = false, length = 9)
    private String voterId;
  
    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    private String suffixName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 9,unique = true,nullable = false)
    private String dmvNumber;

    @Column(length = 9,unique = true,nullable = false)
    private String ssnNumber;

    @Column(unique = true)
    private String email;

    @Column(length = 11, unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean hasVotedBefore;

    @Column(length = 4)
    private String firstVotedYear;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    @OneToMany(mappedBy = "voter")
    private Set<Address> address;

    private String image;

    @Column(nullable = false)
    private String signature;

    @PrePersist
    public void createVoterID(){
        if(this.voterId==null)
            this.voterId = VoterIdGenerator.getNextId();
    }
}
