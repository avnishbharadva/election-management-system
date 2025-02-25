package com.ems.dtos;

import com.ems.entities.CandidateAddress;
import com.ems.entities.CandidateName;
import com.ems.entities.constants.Gender;
import lombok.Data;

@Data
public class CandidateByPartyDTO {

    private CandidateName candidateName;
    private CandidateAddress residentialAddress;
    private CandidateAddress mailingAddress;
    private Gender gender;
}
