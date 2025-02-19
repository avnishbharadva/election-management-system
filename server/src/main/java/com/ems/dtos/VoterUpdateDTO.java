package com.ems.dtos;

import com.ems.entities.constants.Gender;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class VoterUpdateDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private String suffixName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private Boolean hasVotedBefore;
    private String firstVotedYear;
    private Long partyId;
    private String image;
    private String signature;
}
