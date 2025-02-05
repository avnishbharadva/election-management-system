package com.ems.dto;

import com.ems.entities.CandidateName;
import com.ems.entities.Gender;
import com.ems.entities.MaritialStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CandidateDTO {


    private Long candidateId;
    private CandidateName candidateName;
    private String candidateSSN;
    private Date dateOfBirth;
    private Gender gender;
    private MaritialStatus maritialStatus;
    private int noOfChildren;
    private String spouseName;
    private Long partyId;
    private String stateName;
    private String candidateEmail;
    private Long electionId;
    private Long bankDetailsId;
}

