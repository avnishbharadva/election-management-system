package com.ems.dtos;

import com.ems.entities.constants.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressDTO {
    private Long addressId;

    @NotBlank(message = "Address Line Can not be blank")
    private String addressLine;

    private String aptNumber;

    @NotBlank(message = "City can not be blank")
    private String city;

    @NotBlank(message = "County can not be blank")
    private String county;

    private String state = "New York";

    @NotBlank(message = "ZipCode can not be blank")
    @Pattern(regexp = "\\d{5}", message = "Zip Code must be exactly 5 digits")
    private String zipCode;

    @NotNull(message = "Address Type can not be null")
    private AddressType addressType;

    private Long voterId;
}
