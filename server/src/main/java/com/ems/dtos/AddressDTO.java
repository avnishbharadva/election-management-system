package com.ems.dtos;

import com.ems.entities.constants.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private Long addressId;

    private String addressLine;

    private String aptNumber;

    private String city;

    private String county;

    private String state = "New York";

    private String zipCode;

    private AddressType addressType;

    private Long voterId;
}
