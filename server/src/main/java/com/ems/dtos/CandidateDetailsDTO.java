package com.ems.dtos;

import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CandidateDetailsDTO {

    private Long candidateId; // Assuming it's auto-generated

    @NotNull(message = "Candidate name cannot be null")
    private CandidateName candidateName;

    @NotNull(message = "SSN cannot be null")
    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    @NotNull(message = "Gender cannot be null")
    private Gender gender;

    @Pattern(regexp = "^[A-Za-z ]*$", message = "Spouse name must contain only alphabets and spaces")
    private String spouseName; // Nullable, so no @NotNull

    @NotBlank(message = "Party name cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Party name must contain only alphabets and spaces")
    private String partyName;

    @NotBlank(message = "State name cannot be blank")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "State name must contain only alphabets and spaces")
    private String stateName;

    @NotNull(message = "Candidate email cannot be null")
    @NotBlank(message = "Candidate email cannot be blank")
    @Email(message = "Must be a valid email format")
    private String candidateEmail;

    @NotBlank(message = "Election name cannot be blank")
    private String electionName;
}
