package com.ems.dto;

import com.ems.entities.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateAddressDTO {

    private Long addressId;

    @NotNull(message = "Address type cannot be null")
    private AddressType addressType;

    private Long candidateId;

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 255, message = "Street name too long")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City name too long")
    private String city;

    @NotNull(message = "Zipcode cannot be null")
    @Size(max = 5, min = 5, message = "Zipcode must contain 5 digits only")
    private String zipcode;
}
