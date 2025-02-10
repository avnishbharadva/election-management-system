package com.ems.dtos;

import com.ems.entities.BankDetails;
import com.ems.entities.CandidateAddress;
import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritialStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class CandidateDTO {

    private Long candidateId;
    @NotEmpty
    private CandidateName candidateName;

    @NotEmpty
    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    @Past
    @NotEmpty
    private Date dateOfBirth;

    @NotEmpty
    private Gender gender;

    @NotEmpty
    private MaritialStatus maritialStatus;
    private int noOfChildren;
    @Pattern(regexp = "^[a-zA-Z]$",message = "Name must have alphabets")
    private String spouseName;

    @NotEmpty
    private Long partyId;

    @NotEmpty
    private CandidateAddress residentialAddress;
    @NotEmpty
    private CandidateAddress mailingAddress;

    @NotEmpty
    private String stateName;

    @NotEmpty
    @Email(message = "Must be a valid email")
    private String candidateEmail;

    @NotNull
    private Long electionId;
    @NotEmpty
    private BankDetails bankDetails;
}
