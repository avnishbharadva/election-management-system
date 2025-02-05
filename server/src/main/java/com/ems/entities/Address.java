package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Address extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    @Column(nullable = false)
    private String addressLine;

    private String aptNumber;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String county;

    @Column(columnDefinition = "varchar(8) default 'new york'")
    private String state;

    @Column(length = 5, nullable = false)
    private String zipCode;

    @Column(columnDefinition = "boolean default false")
    private boolean isMailing;

    @ManyToOne
    @JoinColumn(name = "voter_id")
    private Voter voter;
}
