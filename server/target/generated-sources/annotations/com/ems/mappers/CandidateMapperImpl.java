package com.ems.mappers;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidateDetailsDTO;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.entities.Election;
import com.ems.entities.Party;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-18T10:34:48+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
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
        candidateDTO.setElectionId( mapElectionToElectionId( candidate.getElection() ) );
        candidateDTO.setBankDetails( candidate.getBankDetails() );
        candidateDTO.setPartyName( candidatePartyPartyName( candidate ) );
        candidateDTO.setElectionName( candidateElectionElectionName( candidate ) );
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
        candidateDTO.setCandidateImage( candidate.getCandidateImage() );
        candidateDTO.setCandidateSignature( candidate.getCandidateSignature() );

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
        candidate.setCandidateImage( candidateDTO.getCandidateImage() );
        candidate.setMaritialStatus( candidateDTO.getMaritialStatus() );
        candidate.setNoOfChildren( candidateDTO.getNoOfChildren() );
        candidate.setSpouseName( candidateDTO.getSpouseName() );
        candidate.setStateName( candidateDTO.getStateName() );
        candidate.setCandidateEmail( candidateDTO.getCandidateEmail() );
        candidate.setResidentialAddress( candidateDTO.getResidentialAddress() );
        candidate.setMailingAddress( candidateDTO.getMailingAddress() );
        candidate.setBankDetails( candidateDTO.getBankDetails() );
        candidate.setCandidateSignature( candidateDTO.getCandidateSignature() );

        return candidate;
    }

    @Override
    public CandidateDetailsDTO toCandidateDetailsDTO(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateDetailsDTO candidateDetailsDTO = new CandidateDetailsDTO();

        candidateDetailsDTO.setPartyName( candidatePartyPartyName( candidate ) );
        candidateDetailsDTO.setElectionName( candidateElectionElectionName( candidate ) );
        candidateDetailsDTO.setCandidateId( candidate.getCandidateId() );
        candidateDetailsDTO.setCandidateName( candidate.getCandidateName() );
        candidateDetailsDTO.setCandidateSSN( candidate.getCandidateSSN() );
        candidateDetailsDTO.setGender( candidate.getGender() );
        candidateDetailsDTO.setSpouseName( candidate.getSpouseName() );
        candidateDetailsDTO.setStateName( candidate.getStateName() );
        candidateDetailsDTO.setCandidateEmail( candidate.getCandidateEmail() );

        return candidateDetailsDTO;
    }

    @Override
    public void updateCandidateFromDTO(CandidateDTO candidateDTO, Candidate candidate) {
        if ( candidateDTO == null ) {
            return;
        }

        if ( candidateDTO.getCandidateId() != null ) {
            candidate.setCandidateId( candidateDTO.getCandidateId() );
        }
        if ( candidateDTO.getCandidateName() != null ) {
            if ( candidate.getCandidateName() == null ) {
                candidate.setCandidateName( new CandidateName() );
            }
            updateCandidateNameFromDTO( candidateDTO.getCandidateName(), candidate.getCandidateName() );
        }
        if ( candidateDTO.getCandidateSSN() != null ) {
            candidate.setCandidateSSN( candidateDTO.getCandidateSSN() );
        }
        if ( candidateDTO.getDateOfBirth() != null ) {
            candidate.setDateOfBirth( candidateDTO.getDateOfBirth() );
        }
        if ( candidateDTO.getGender() != null ) {
            candidate.setGender( candidateDTO.getGender() );
        }
        if ( candidateDTO.getCandidateImage() != null ) {
            candidate.setCandidateImage( candidateDTO.getCandidateImage() );
        }
        if ( candidateDTO.getMaritialStatus() != null ) {
            candidate.setMaritialStatus( candidateDTO.getMaritialStatus() );
        }
        candidate.setNoOfChildren( candidateDTO.getNoOfChildren() );
        if ( candidateDTO.getSpouseName() != null ) {
            candidate.setSpouseName( candidateDTO.getSpouseName() );
        }
        if ( candidateDTO.getStateName() != null ) {
            candidate.setStateName( candidateDTO.getStateName() );
        }
        if ( candidateDTO.getCandidateEmail() != null ) {
            candidate.setCandidateEmail( candidateDTO.getCandidateEmail() );
        }
        if ( candidateDTO.getResidentialAddress() != null ) {
            candidate.setResidentialAddress( candidateDTO.getResidentialAddress() );
        }
        if ( candidateDTO.getMailingAddress() != null ) {
            candidate.setMailingAddress( candidateDTO.getMailingAddress() );
        }
        if ( candidateDTO.getBankDetails() != null ) {
            candidate.setBankDetails( candidateDTO.getBankDetails() );
        }
        if ( candidateDTO.getCandidateSignature() != null ) {
            candidate.setCandidateSignature( candidateDTO.getCandidateSignature() );
        }
    }

    @Override
    public void updateCandidateNameFromDTO(CandidateName newName, CandidateName existingName) {
        if ( newName == null ) {
            return;
        }

        if ( newName.getFirstName() != null ) {
            existingName.setFirstName( newName.getFirstName() );
        }
        if ( newName.getMiddleName() != null ) {
            existingName.setMiddleName( newName.getMiddleName() );
        }
        if ( newName.getLastName() != null ) {
            existingName.setLastName( newName.getLastName() );
        }
    }

    @Override
    public Election toElection(ElectionSortDTO electionSortDTO) {
        if ( electionSortDTO == null ) {
            return null;
        }

        Election election = new Election();

        election.setElectionId( electionSortDTO.getElectionId() );
        election.setElectionName( electionSortDTO.getElectionName() );
        election.setElectionType( electionSortDTO.getElectionType() );
        if ( electionSortDTO.getElectionDate() != null ) {
            election.setElectionDate( LocalDateTime.ofInstant( electionSortDTO.getElectionDate().toInstant(), ZoneOffset.UTC ).toLocalDate() );
        }
        election.setElectionState( electionSortDTO.getElectionState() );
        election.setTotalSeats( electionSortDTO.getTotalSeats() );

        return election;
    }

    @Override
    public org.openapitools.model.ElectionSortDTO toElectionSortDTO(Election election) {
        if ( election == null ) {
            return null;
        }

        org.openapitools.model.ElectionSortDTO electionSortDTO = new org.openapitools.model.ElectionSortDTO();

        if ( election.getElectionId() != null ) {
            electionSortDTO.setElectionId( election.getElectionId().intValue() );
        }
        electionSortDTO.setElectionName( election.getElectionName() );
        electionSortDTO.setElectionType( election.getElectionType() );
        electionSortDTO.setElectionDate( election.getElectionDate() );
        electionSortDTO.setElectionState( election.getElectionState() );
        electionSortDTO.setTotalSeats( election.getTotalSeats() );

        return electionSortDTO;
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

    private String candidatePartyPartyName(Candidate candidate) {
        Party party = candidate.getParty();
        if ( party == null ) {
            return null;
        }
        return party.getPartyName();
    }

    private String candidateElectionElectionName(Candidate candidate) {
        Election election = candidate.getElection();
        if ( election == null ) {
            return null;
        }
        return election.getElectionName();
    }
}
