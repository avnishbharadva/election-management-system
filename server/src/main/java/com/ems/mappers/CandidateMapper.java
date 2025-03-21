package com.ems.mappers;

import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.entities.Election;
import com.ems.entities.Party;
import org.mapstruct.*;
import org.openapitools.model.*;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "partyId", source = "party")
    @Mapping(target = "electionId", source = "election")
    @Mapping(target = "bankDetails", source = "bankDetails")
    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    CandidateDto toCandidateDto(Candidate candidate);

    Candidate toCandidate(CandidateDto candidateDto);
    com.ems.entities.CandidateAddress toCandidateAddress(CandidateAddress candidateAddress);
    com.ems.entities.CandidateAddress toCandidateAddress(CandidateAddressNoValidation candidateAddress);

    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    CandidateDetailsDto toCandidateDetailsDto(Candidate candidate);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateFromDto(CandidateUpdateDto candidateDto, @MappingTarget Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateNameFromDto(CandidateName newName, @MappingTarget CandidateName existingName);


    ElectionSortDTO toElectionSortDTO(Election election);


    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }

    default Long mapElectionToElectionId(Election election) {
        return (election != null) ? election.getElectionId() : null;
    }
}
