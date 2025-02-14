package com.ems.dtos;

import com.ems.entities.constants.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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

    private Boolean hasVotedBefore;

    private Long firstVotedYear;

    private Long partyId;

    private AddressDTO residentialAddress;

    private AddressDTO mailingAddress;

    private String image;

    private String signature;
}
