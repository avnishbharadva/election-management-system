package com.ems.dtos;

import com.ems.entities.constants.Gender;
import lombok.Data;
import java.time.LocalDate;

@Data
public class VoterDTO {
    private String voterId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffixName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String dmvNumber;
    private String ssnNumber;
    private String email;
    private String phoneNumber;
    private boolean hasVotedBefore;
    private String firstVotedYear;
    private long partyId;
    private String image;
    private String signature;
}
