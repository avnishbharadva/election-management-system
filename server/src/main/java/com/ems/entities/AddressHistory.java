package com.ems.entities;

import com.ems.entities.constants.AddressType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AddressHistory extends TimeStamp{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    private String voterId;

    private String addressLine;

    private String aptNumber;

    private String city;

    private String county;

    private String state = "New York";

    private String zipCode;

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
}
