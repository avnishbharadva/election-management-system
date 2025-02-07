package com.ems.dtos;

import com.ems.entities.constants.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VoterRegisterDTO {

    @NotEmpty(message = "First Name cannot be empty")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;

    @Size(max = 20, message = "Middle Name must not exceed 20 characters")
    private String middleName;

    @NotEmpty(message = "Last Name can not be empty")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;

    @NotEmpty(message = "Suffix Name can not be empty")
    @Size(max = 10, message = "Suffix Name must not exceed 10 characters")
    private String suffixName;

    @NotNull(message = "Date of Birth cannot be null")
    @Past(message = "Date of Birth must be a past date")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotEmpty(message = "DMV Number cannot be empty")
    @Pattern(regexp = "\\d{9}", message = "DMV Number must be exactly 9 digits")
    private String dmvNumber;

    @NotBlank(message = "SSN Number cannot be empty")
    @Pattern(regexp = "\\d{9}", message = "SSN Number must be exactly 9 digits")
    private String ssnNumber;

    @Email
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "Phone Number cannot be empty")
    @Pattern(regexp = "\\d{11}", message = "Phone Number must be exactly 11 digits")
    private String phoneNumber;

    private boolean hasVotedBefore;

    @Pattern(regexp = "\\d{4}", message = "Voted Year must be a 4-digit year")
    private String firstVotedYear;

    @Positive(message = "Party ID must be a positive number")
    private long partyId;

    @NotNull(message = "Address cannot be null")
    @Size(min = 1, message = "At least one address is required")
    private List<AddressDTO> address;

    @NotEmpty(message = "Image url can not be empty")
    private String image;

    @NotEmpty(message = "Signature url can not be empty")
    private String signature;
}
