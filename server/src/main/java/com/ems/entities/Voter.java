package com.ems.entities;

import com.ems.entities.constants.Gender;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Voter extends AuditEntity {
    @Id
    @Column(unique = true, nullable = false, length = 9)
    private String voterId;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(length = 20)
    private String middleName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(length = 10)
    private String suffixName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 9, unique = true, nullable = false)
    private String dmvNumber;

    @Column(length = 9, unique = true, nullable = false)
    private String ssnNumber;

    @Column(unique = true, length = 50)
    private String email;

    @Column(length = 11, unique = true)
    private String phoneNumber;

    @Column(columnDefinition = "boolean default false")
    private boolean hasVotedBefore;

    @Column(precision = 4)
    private Long firstVotedYear;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

<<<<<<< HEAD
    @OneToMany(mappedBy = "voter")
    @JsonManagedReference("address-voter")
    @ToString.Exclude
    private List<Address> address;
=======
    @OneToOne
    @JoinColumn(name = "residential_address")
    private Address residentialAddress;

    @OneToOne
    @JoinColumn(name = "mailing_address")
    private Address mailingAddress;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

    private String image;

    private String signature;

<<<<<<< HEAD

=======
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    @ManyToOne
    @JoinColumn(name = "voter_status_id")
    private VoterStatus voterStatus;

    @PrePersist
    public void createVoterID() {
        if (this.voterId == null) {
            this.voterId = VoterIdGenerator.getNextId();
        }
    }

    public Long getPartyId() {
        return (party != null) ? party.getPartyId() : null;
    }

    public Long getVoterStatusId() {
        return (voterStatus != null) ? voterStatus.getStatusId() : null;
    }

    public String getGenderAsString() {
        return (gender != null) ? gender.name() : null;
    }

    public LocalDateTime getCreated_at() {
        return super.getCreatedAt();

    }

    public LocalDateTime getUpdated_at() {
        return super.getUpdatedAt();
    }
}

