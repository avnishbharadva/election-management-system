package com.ems.mappers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-19T11:46:53+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CandidateMapperImpl implements CandidateMapper {

    @Override
    public CandidateDTO toCandidateDTO(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateDTO candidateDTO = new CandidateDTO();

        candidateDTO.setPartyId( mapPartyToId( candidate.getParty() ) );
        candidateDTO.setElectionId( mapElectionTOElectionId( candidate.getElection() ) );
        candidateDTO.setBankDetails( candidate.getBankDetails() );
        candidateDTO.setCandidateId( candidate.getCandidateId() );
        candidateDTO.setCandidateName( candidate.getCandidateName() );
        candidateDTO.setCandidateSSN( candidate.getCandidateSSN() );
        candidateDTO.setDateOfBirth( candidate.getDateOfBirth() );
        candidateDTO.setGender( candidate.getGender() );
        candidateDTO.setMaritialStatus( candidate.getMaritialStatus() );
        candidateDTO.setNoOfChildren( candidate.getNoOfChildren() );
        candidateDTO.setSpouseName( candidate.getSpouseName() );
        candidateDTO.setResidentialAddress( candidate.getResidentialAddress() );
        candidateDTO.setMailingAddress( candidate.getMailingAddress() );
        candidateDTO.setStateName( candidate.getStateName() );
        candidateDTO.setCandidateEmail( candidate.getCandidateEmail() );

        return candidateDTO;
    }

    @Override
    public Candidate toCandidate(CandidateDTO candidateDTO) {
        if ( candidateDTO == null ) {
            return null;
        }

        Candidate candidate = new Candidate();

        candidate.setCandidateId( candidateDTO.getCandidateId() );
        candidate.setCandidateName( candidateDTO.getCandidateName() );
        candidate.setCandidateSSN( candidateDTO.getCandidateSSN() );
        candidate.setDateOfBirth( candidateDTO.getDateOfBirth() );
        candidate.setGender( candidateDTO.getGender() );
        candidate.setMaritialStatus( candidateDTO.getMaritialStatus() );
        candidate.setNoOfChildren( candidateDTO.getNoOfChildren() );
        candidate.setSpouseName( candidateDTO.getSpouseName() );
        candidate.setStateName( candidateDTO.getStateName() );
        candidate.setCandidateEmail( candidateDTO.getCandidateEmail() );
        candidate.setResidentialAddress( candidateDTO.getResidentialAddress() );
        candidate.setMailingAddress( candidateDTO.getMailingAddress() );
        candidate.setBankDetails( candidateDTO.getBankDetails() );

        return candidate;
    }

    @Override
    public CandidateByPartyDTO toCandidateByPartyDTO(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateByPartyDTO candidateByPartyDTO = new CandidateByPartyDTO();

        candidateByPartyDTO.setCandidateName( candidate.getCandidateName() );
        candidateByPartyDTO.setResidentialAddress( candidate.getResidentialAddress() );
        candidateByPartyDTO.setMailingAddress( candidate.getMailingAddress() );
        candidateByPartyDTO.setGender( candidate.getGender() );

        return candidateByPartyDTO;
    }
}
