package com.ems.dtos;

import com.ems.entities.constants.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class VoterRegisterDTO {

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

    private List<AddressDTO> address;

    private String image;

    private String signature;
}
