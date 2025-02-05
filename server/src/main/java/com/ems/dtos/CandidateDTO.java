package com.ems.dtos;

import com.ems.entities.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CandidateDTO {

    private Long candidateId;

    private CandidateName candidateName;

    @Size(min = 9, max = 9)
    private String candidateSSN;

    @Past
    private Date dateOfBirth;

    @Pattern(regexp = "^(Male|Female)$")
    private Gender gender;

    @Pattern(regexp = "^(SINGLE|MARRIED|DIVORCED|WIDOWED)$")
    private MaritialStatus maritialStatus;

    private int noOfChildren;

    private String spouseName;

    private Long partyId; // Updated to store partyId instead of Party object directly

    private List<CandidateAddress> candidateAddress; // List of addresses

    private String stateName;

    @Email(message = "Must be an Email")
    private String candidateEmail;

    private Long electionId;

    private BankDetails bankDetails;
}
