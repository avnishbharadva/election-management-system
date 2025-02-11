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
    private CandidateName candidateName;

    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    @Past
    private Date dateOfBirth;

    private Gender gender;
    private MaritialStatus maritialStatus;
    private int noOfChildren;

    @Pattern(regexp = "^[A-Za-z]+$", message = "Name must have alphabets")
    private String spouseName;

    private Long partyId;
    private Long electionId;
    private CandidateAddress residentialAddress;
    private CandidateAddress mailingAddress;
    private String stateName;

    @Email(message = "Must be a valid email")
    private String candidateEmail;
    private BankDetails bankDetails;
    private String candidateSignature;
    private String candidateImage;
}
