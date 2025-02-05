package com.ems.mappers;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.entities.Party;
import com.ems.entities.CandidateAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateMapper {
    @Mapping(target = "partyId", source = "party")
    @Mapping(target = "candidateAddress", source = "address")
    CandidateDTO toCandidateDTO(Candidate candidate);

    Candidate toCandidate(CandidateDTO candidateDTO);

    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }

    List<CandidateAddress> mapAddressList(List<CandidateAddress> addressList);
}
