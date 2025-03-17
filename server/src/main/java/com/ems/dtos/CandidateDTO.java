package com.ems.dtos;

import com.ems.entities.BankDetails;
import com.ems.entities.CandidateAddress;
import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritialStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDTO {

    private Long candidateId; // Optional, assuming it's auto-generated

    @NotNull(message = "Candidate name cannot be null")
    private CandidateName candidateName;

    @NotNull(message = "SSN cannot be null")
    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @NotNull(message = "Marital status cannot be null")
    private MaritialStatus maritialStatus;

    @Min(value = 0, message = "Number of children cannot be negative")
    private int noOfChildren;

    @Pattern(regexp = "^[A-Za-z ]*$", message = "Spouse name must contain only alphabets and spaces")
    private String spouseName;

    @NotNull(message = "Party ID cannot be null")
    private Long partyId;

    @NotNull(message = "Residential address cannot be null")
    private CandidateAddress residentialAddress;

    @NotNull(message = "Mailing address cannot be null")
    private CandidateAddress mailingAddress;

    @NotBlank(message = "State name cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "State name must contain only alphabets and spaces")
    private String stateName;

    @NotNull(message = "Candidate email cannot be null")
    @NotBlank(message = "Candidate email cannot be blank")
    @Email(message = "Must be a valid email format")
    private String candidateEmail;

    @NotNull(message = "Election ID cannot be null")
    private Long electionId;
    private String electionName;
    private String partyName;

    @NotNull(message = "Bank details cannot be null")
    private BankDetails bankDetails;

    private String candidateImage;
    private String candidateSignature;
}
