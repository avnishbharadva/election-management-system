package com.ems.mappers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    @Mapping(target = "partyId", source = "party")
    @Mapping(target ="electionId",source = "election")
    @Mapping(target = "bankDetails",source = "bankDetails")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    CandidateDTO toCandidateDTO(Candidate candidate);


    default LocalDate mapDateOfBirth(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    Candidate toCandidate(CandidateDTO candidateDTO);

    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }


    default Long mapElectionTOElectionId(Election election)
    {
        return (election!=null)?election.getElectionId():null;
    }

    List<CandidateAddress> mapAddressList(List<CandidateAddress> addressList);
}
