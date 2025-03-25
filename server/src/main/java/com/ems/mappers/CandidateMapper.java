package com.ems.mappers;

import com.ems.entities.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "partyId", source = "party")
    @Mapping(target = "electionId", source = "election")
    @Mapping(target = "bankDetails", source = "bankDetails")
    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    org.openapitools.model.CandidateDto toCandidateDto(Candidate candidate);

    Candidate toCandidate(org.openapitools.model.CandidateDto candidateDto);
    com.ems.entities.CandidateAddress toCandidateAddress(CandidateAddress candidateAddress);
    com.ems.entities.CandidateAddress toCandidateAddress(org.openapitools.model.CandidateAddressNoValidation candidateAddress);

    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    org.openapitools.model.CandidateDetailsDto toCandidateDetailsDto(Candidate candidate);



    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateFromDto(org.openapitools.model.CandidateUpdateDto candidateDto, @MappingTarget Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateNameFromDto(CandidateName newName, @MappingTarget CandidateName existingName);


    org.openapitools.model.ElectionSortDTO toElectionSortDTO(Election election);


    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }

    default Long mapElectionToElectionId(Election election) {
        return (election != null) ? election.getElectionId() : null;
    }
}
