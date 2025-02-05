package com.ems.dto;

import com.ems.entities.CandidateName;
import com.ems.entities.Gender;
import com.ems.entities.MaritialStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class CandidateDTO {



    private Long candidateId;

    @Pattern(regexp = "^[a-zA-Z.]+$", message = "Name must contain alphabets ")
    private CandidateName candidateName;

    @Size(min=9,max =9)
    private String candidateSSN;


    @Past
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Pattern(regexp = "^(Male|Female)$")
    private Gender gender;

    @Pattern(regexp = "^(SINGLE|MARRIED|DIVORCED|WIDOWED)")
    private MaritialStatus maritialStatus;

    private int noOfChildren;

    @Pattern(regexp = "^[a-zA-Z.]+$", message = "Name must contain alphabets ")
    private String spouseName;

    private Long partyId;

    private String stateName;

    @Email(message = "Must be an Email")
    private String candidateEmail;

    private Long electionId;

    private Long bankDetailsId;
}

