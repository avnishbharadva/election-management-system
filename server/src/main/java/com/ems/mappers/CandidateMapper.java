package com.ems.mappers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Candidate;
import com.ems.entities.Election;
import com.ems.entities.Party;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "partyId", source = "party")
    @Mapping(target = "electionId", source = "election")
    @Mapping(target = "bankDetails", source = "bankDetails")
    CandidateDTO toCandidateDTO(Candidate candidate);

    Candidate toCandidate(CandidateDTO candidateDTO);

    Election toElection(ElectionSortDTO electionSortDTO);
    ElectionSortDTO toElectionSortDTO(Election election);

    CandidateSearchDTO toCandidateSearchDTO(Candidate candidate);
    Candidate toCandidateFromSearch(CandidateSearchDTO candidateSearchDTO);


    default Long mapPartyToId(Party party) {
        return (party != null) ? party.getPartyId() : null;
    }

    default Long mapElectionTOElectionId(Election election) {
        return (election != null) ? election.getElectionId() : null;
    }

    CandidateByPartyDTO toCandidateByPartyDTO(Candidate candidate);

}

