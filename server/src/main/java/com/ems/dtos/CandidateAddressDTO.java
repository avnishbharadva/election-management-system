package com.ems.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CandidateAddressDTO {

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 255, message = "Street name is too long")
    private String street;

    @NotBlank(message = "City cannot be blank")
    @Size(max = 100, message = "City name is too long")
    private String city;

    @NotNull(message = "Zipcode cannot be null")
    @Size(min = 5, max = 5, message = "Zipcode must contain exactly 5 digits")
    private String zipcode;

}
