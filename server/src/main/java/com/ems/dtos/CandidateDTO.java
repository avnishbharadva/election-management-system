package com.ems.dtos;

import com.ems.entities.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class CandidateDTO {

    private Long candidateId;
    private CandidateName candidateName;

    @Size(min = 9, max = 9, message = "SSN must have exactly 9 digits")
    private String candidateSSN;

    @Past
    private Date dateOfBirth;

   private Gender gender;

    private MaritialStatus maritialStatus;

    private int noOfChildren;
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
