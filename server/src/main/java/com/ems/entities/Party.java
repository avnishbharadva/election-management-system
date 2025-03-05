package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Party extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long partyId;

    @Column(length = 30, nullable = false)
    private String partyName;

    @Column(length = 10, nullable = false)
    private String partyAbbreviation;

    @Column(length = 200, nullable = false)
    private String partySymbol;

    @Column(length = 4, nullable = false)
    private Integer partyFoundationYear;

    @Column(length = 200)
    private String partyWebSite;

    @Column(length = 50, nullable = false)
    private String headQuarters;

    @Column(length = 30,nullable = false)
    private String founderName;


}
