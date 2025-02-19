package com.ems.dtos;

import com.ems.entities.constants.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class VoterRegisterDTO {

    @NotBlank(message = "First Name cannot be blank")
    @Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    private String firstName;

    @Size(max = 20, message = "Middle Name must not exceed 20 characters")
    private String middleName;

    @NotBlank(message = "Last Name can not be blank")
    @Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Suffix Name can not be blank")
    @Size(max = 10, message = "Suffix Name must not exceed 10 characters")
    private String suffixName;

    @NotNull(message = "Date of Birth cannot be null")
    @Past(message = "Date of Birth must be a past date")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotBlank(message = "DMV Number cannot be blank")
    @Pattern(regexp = "\\d{9}", message = "DMV Number must be exactly 9 digits")
    private String dmvNumber;

    @NotBlank(message = "SSN Number cannot be blank")
    @Pattern(regexp = "\\d{9}", message = "SSN Number must be exactly 9 digits")
    private String ssnNumber;

    @Email
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone Number cannot be blank")
    @Pattern(regexp = "\\d{11}", message = "Phone Number must be exactly 11 digits")
    private String phoneNumber;

    private Boolean hasVotedBefore;

    @Min(value = 1900, message = "Year must be 1900 or later")
    @Max(value = 2025, message = "Year must be 2025 or earlier")
    private Long firstVotedYear;

    @Positive(message = "Party ID must be a positive number")
    private Long partyId;

    @NotNull(message = "Residential Address cannot be empty")
    private AddressDTO residentialAddress;

    private AddressDTO mailingAddress;

    private MultipartFile image;

    private MultipartFile signature;

    private Long statusId;
}
