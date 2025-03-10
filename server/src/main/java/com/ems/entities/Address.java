package com.ems.entities;

import com.ems.entities.constants.AddressType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Address extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false)
    private String addressLine;

    private String aptNumber;

    @Column(nullable = false)
    private String city;

    private String county;

    private String state = "New York";

    @Column(length = 5, nullable = false)
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

//    @ManyToOne
//    @JoinColumn(name = "voter_id")
//    @JsonBackReference("address-voter")
//    private Voter voter;
}
