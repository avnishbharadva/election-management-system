package com.ems.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class    Address extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(nullable = false)
    private String addressLine;

    private String aptNumber;

    @Column(nullable = false)
    private String city;

    private String county;

    private String state = "New York";

    @Column(length = 5, nullable = false)
    private String zipCode;

    private boolean isMailing;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "voter_id")
    private Voter voter;
}
