package com.ems.entities;

import com.ems.entities.constants.AddressType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Address extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    @Column(nullable = false)
    private String addressLine;

    private String aptNumber;

    @Column(nullable = false)
    private String city;

    private String state = "New York";

    private String county;

    private String town;

    @Column(length = 5, nullable = false)
    private String zipCode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

<<<<<<< HEAD
    @ManyToOne
    @JoinColumn(name = "voter_id")
    @JsonBackReference("address-voter")
    private Voter voter;
=======
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
