package com.ems.dtos;

import com.ems.entities.constants.AddressType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    private String addressLine;

    private String aptNumber;

    private String city;

    private String county;

    private String zipCode;

    private AddressType addressType;
}
