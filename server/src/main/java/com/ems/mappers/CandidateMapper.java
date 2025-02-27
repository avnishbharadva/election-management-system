package com.ems.mappers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidateDetailsDTO;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.entities.Election;
import com.ems.entities.Party;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "partyId", source = "party")
    @Mapping(target = "electionId", source = "election")
    @Mapping(target = "bankDetails", source = "bankDetails")

    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    CandidateDTO toCandidateDTO(Candidate candidate);

    Candidate toCandidate(CandidateDTO candidateDTO);

    @Mapping(target = "partyName", source = "party.partyName")
    @Mapping(target = "electionName", source = "election.electionName")
    CandidateDetailsDTO toCandidateDetailsDTO(Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateFromDTO(CandidateDTO candidateDTO, @MappingTarget Candidate candidate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCandidateNameFromDTO(CandidateName newName, @MappingTarget CandidateName existingName);

    Election toElection(ElectionSortDTO electionSortDTO);
    ElectionSortDTO toElectionSortDTO(Election election);

    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }

    default Long mapElectionToElectionId(Election election) {
        return (election != null) ? election.getElectionId() : null;
    }
    CandidateByPartyDTO toCandidateByPartyDTO(Candidate candidate);
}
