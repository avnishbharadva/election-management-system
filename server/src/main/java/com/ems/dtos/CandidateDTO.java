package com.ems.dtos;

import com.ems.entities.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
public class CandidateDTO {

    private Long candidateId;
    private CandidateName candidateName;

    @Pattern(regexp = "^[0-9]{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;


    @Past
    private Date dateOfBirth;

   private Gender gender;

    private MaritialStatus maritialStatus;

    private int noOfChildren;


    @Pattern(regexp = "^[a-zA-Z]$",message = "Name must have alphabets")
    private String spouseName;

    private Long partyId;

    private CandidateAddress residentialAddress;
    private CandidateAddress mailingAddress;

    private String stateName;

    @Email(message = "Must be a valid email")
    private String candidateEmail;

    private Long electionId;
    private BankDetails bankDetails;
}
