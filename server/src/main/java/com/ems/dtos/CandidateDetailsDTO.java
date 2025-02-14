package com.ems.dtos;

import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateDetailsDTO {

    private Long candidateId;

    private CandidateName candidateName;

    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    private Gender gender;

    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must have alphabets")
    private String spouseName;

    private String partyName;

    private String stateName;

    @Email(message = "Must be a valid email")
    private String candidateEmail;

    private String electionName;


}
