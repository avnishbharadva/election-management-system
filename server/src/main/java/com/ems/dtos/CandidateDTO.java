package com.ems.dtos;

import com.ems.entities.BankDetails;
import com.ems.entities.CandidateAddress;
import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.MaritialStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class CandidateDTO {

    private Long candidateId;
//    @NotNull
    private CandidateName candidateName;

//    @NotNull
    @Pattern(regexp = "^\\d{9}$", message = "SSN must contain exactly 9 digits")
    private String candidateSSN;

    @Past
//    @NotNull
    private Date dateOfBirth;

//    @NotNull
    private Gender gender;

    private MaritialStatus maritialStatus;
    private int noOfChildren;
    @Pattern(regexp = "^[A-Za-z]+$",message = "Name must have alphabets")
    private String spouseName;

//    @NotNull
    private Long partyId;

//    @NotNull
    private CandidateAddress residentialAddress;
//    @NotNull
    private CandidateAddress mailingAddress;

//    @NotNull
    private String stateName;

//    @NotNull
    @Email(message = "Must be a valid email")
    private String candidateEmail;

//    @NotNull
    private Long electionId;
//    @NotNull
    private BankDetails bankDetails;

    private String candidateSignature;
    private String candidateImage;
}
