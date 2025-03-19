package com.ems.mappers;

import com.ems.entities.BankDetails;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.entities.Election;
import com.ems.entities.Party;
import com.ems.entities.constants.MaritalStatus;
import javax.annotation.processing.Generated;
import org.openapitools.model.BankDetailsNoValidation;
import org.openapitools.model.CandidateAddress;
import org.openapitools.model.CandidateAddressNoValidation;
import org.openapitools.model.CandidateDetailsDto;
import org.openapitools.model.CandidateDto;
import org.openapitools.model.CandidateNameNoValidation;
import org.openapitools.model.CandidateUpdateDto;
import org.openapitools.model.ElectionSortDTO;
import org.openapitools.model.Gender;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-18T23:37:19+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CandidateMapperImpl implements CandidateMapper {

    @Override
    public CandidateDto toCandidateDto(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateDto candidateDto = new CandidateDto();

        candidateDto.setPartyId( mapPartyToId( candidate.getParty() ) );
        candidateDto.setElectionId( mapElectionToElectionId( candidate.getElection() ) );
        candidateDto.setBankDetails( bankDetailsToBankDetails( candidate.getBankDetails() ) );
        candidateDto.setPartyName( candidatePartyPartyName( candidate ) );
        candidateDto.setElectionName( candidateElectionElectionName( candidate ) );
        candidateDto.setCandidateId( candidate.getCandidateId() );
        candidateDto.setCandidateName( candidateNameToCandidateName( candidate.getCandidateName() ) );
        candidateDto.setCandidateSSN( candidate.getCandidateSSN() );
        candidateDto.setDateOfBirth( candidate.getDateOfBirth() );
        candidateDto.setGender( genderToGender( candidate.getGender() ) );
        candidateDto.setMaritalStatus( maritalStatusToMaritalStatus( candidate.getMaritalStatus() ) );
        candidateDto.setNoOfChildren( candidate.getNoOfChildren() );
        candidateDto.setSpouseName( candidate.getSpouseName() );
        candidateDto.setResidentialAddress( candidateAddressToCandidateAddress( candidate.getResidentialAddress() ) );
        candidateDto.setMailingAddress( candidateAddressToCandidateAddress( candidate.getMailingAddress() ) );
        candidateDto.setStateName( candidate.getStateName() );
        candidateDto.setCandidateEmail( candidate.getCandidateEmail() );
        candidateDto.setCandidateImage( candidate.getCandidateImage() );
        candidateDto.setCandidateSignature( candidate.getCandidateSignature() );

        return candidateDto;
    }

    @Override
    public Candidate toCandidate(CandidateDto candidateDto) {
        if ( candidateDto == null ) {
            return null;
        }

        Candidate candidate = new Candidate();

        candidate.setCandidateId( candidateDto.getCandidateId() );
        candidate.setCandidateName( candidateNameToCandidateName1( candidateDto.getCandidateName() ) );
        candidate.setCandidateSSN( candidateDto.getCandidateSSN() );
        candidate.setDateOfBirth( candidateDto.getDateOfBirth() );
        candidate.setGender( genderToGender1( candidateDto.getGender() ) );
        candidate.setCandidateImage( candidateDto.getCandidateImage() );
        candidate.setMaritalStatus( maritalStatusToMaritalStatus1( candidateDto.getMaritalStatus() ) );
        if ( candidateDto.getNoOfChildren() != null ) {
            candidate.setNoOfChildren( candidateDto.getNoOfChildren() );
        }
        candidate.setSpouseName( candidateDto.getSpouseName() );
        candidate.setStateName( candidateDto.getStateName() );
        candidate.setCandidateEmail( candidateDto.getCandidateEmail() );
        candidate.setResidentialAddress( toCandidateAddress( candidateDto.getResidentialAddress() ) );
        candidate.setMailingAddress( toCandidateAddress( candidateDto.getMailingAddress() ) );
        candidate.setBankDetails( bankDetailsToBankDetails1( candidateDto.getBankDetails() ) );
        candidate.setCandidateSignature( candidateDto.getCandidateSignature() );

        return candidate;
    }

    @Override
    public com.ems.entities.CandidateAddress toCandidateAddress(CandidateAddress candidateAddress) {
        if ( candidateAddress == null ) {
            return null;
        }

        com.ems.entities.CandidateAddress candidateAddress1 = new com.ems.entities.CandidateAddress();

        candidateAddress1.setAddressId( candidateAddress.getAddressId() );
        candidateAddress1.setStreet( candidateAddress.getStreet() );
        candidateAddress1.setCity( candidateAddress.getCity() );
        if ( candidateAddress.getZipcode() != null ) {
            candidateAddress1.setZipcode( candidateAddress.getZipcode() );
        }

        return candidateAddress1;
    }

    @Override
    public com.ems.entities.CandidateAddress toCandidateAddress(CandidateAddressNoValidation candidateAddress) {
        if ( candidateAddress == null ) {
            return null;
        }

        com.ems.entities.CandidateAddress candidateAddress1 = new com.ems.entities.CandidateAddress();

        candidateAddress1.setAddressId( candidateAddress.getAddressId() );
        candidateAddress1.setStreet( candidateAddress.getStreet() );
        candidateAddress1.setCity( candidateAddress.getCity() );
        if ( candidateAddress.getZipcode() != null ) {
            candidateAddress1.setZipcode( candidateAddress.getZipcode() );
        }

        return candidateAddress1;
    }

    @Override
    public CandidateDetailsDto toCandidateDetailsDto(Candidate candidate) {
        if ( candidate == null ) {
            return null;
        }

        CandidateDetailsDto candidateDetailsDto = new CandidateDetailsDto();

        candidateDetailsDto.setPartyName( candidatePartyPartyName( candidate ) );
        candidateDetailsDto.setElectionName( candidateElectionElectionName( candidate ) );
        candidateDetailsDto.setCandidateId( candidate.getCandidateId() );
        candidateDetailsDto.setCandidateName( candidateNameToCandidateName( candidate.getCandidateName() ) );
        candidateDetailsDto.setCandidateSSN( candidate.getCandidateSSN() );
        candidateDetailsDto.setGender( genderToGender( candidate.getGender() ) );
        candidateDetailsDto.setSpouseName( candidate.getSpouseName() );
        candidateDetailsDto.setStateName( candidate.getStateName() );
        candidateDetailsDto.setCandidateEmail( candidate.getCandidateEmail() );

        return candidateDetailsDto;
    }

    @Override
    public void updateCandidateFromDto(CandidateUpdateDto candidateDto, Candidate candidate) {
        if ( candidateDto == null ) {
            return;
        }

        if ( candidateDto.getCandidateId() != null ) {
            candidate.setCandidateId( candidateDto.getCandidateId() );
        }
        if ( candidateDto.getCandidateName() != null ) {
            if ( candidate.getCandidateName() == null ) {
                candidate.setCandidateName( new CandidateName() );
            }
            candidateNameNoValidationToCandidateName( candidateDto.getCandidateName(), candidate.getCandidateName() );
        }
        if ( candidateDto.getCandidateSSN() != null ) {
            candidate.setCandidateSSN( candidateDto.getCandidateSSN() );
        }
        if ( candidateDto.getDateOfBirth() != null ) {
            candidate.setDateOfBirth( candidateDto.getDateOfBirth() );
        }
        if ( candidateDto.getGender() != null ) {
            candidate.setGender( genderToGender1( candidateDto.getGender() ) );
        }
        if ( candidateDto.getCandidateImage() != null ) {
            candidate.setCandidateImage( candidateDto.getCandidateImage() );
        }
        if ( candidateDto.getMaritalStatus() != null ) {
            candidate.setMaritalStatus( maritalStatusToMaritalStatus1( candidateDto.getMaritalStatus() ) );
        }
        if ( candidateDto.getNoOfChildren() != null ) {
            candidate.setNoOfChildren( candidateDto.getNoOfChildren() );
        }
        if ( candidateDto.getSpouseName() != null ) {
            candidate.setSpouseName( candidateDto.getSpouseName() );
        }
        if ( candidateDto.getStateName() != null ) {
            candidate.setStateName( candidateDto.getStateName() );
        }
        if ( candidateDto.getCandidateEmail() != null ) {
            candidate.setCandidateEmail( candidateDto.getCandidateEmail() );
        }
        if ( candidateDto.getResidentialAddress() != null ) {
            candidate.setResidentialAddress( toCandidateAddress( candidateDto.getResidentialAddress() ) );
        }
        if ( candidateDto.getMailingAddress() != null ) {
            candidate.setMailingAddress( toCandidateAddress( candidateDto.getMailingAddress() ) );
        }
        if ( candidateDto.getBankDetails() != null ) {
            if ( candidate.getBankDetails() == null ) {
                candidate.setBankDetails( new BankDetails() );
            }
            bankDetailsNoValidationToBankDetails( candidateDto.getBankDetails(), candidate.getBankDetails() );
        }
        if ( candidateDto.getCandidateSignature() != null ) {
            candidate.setCandidateSignature( candidateDto.getCandidateSignature() );
        }
    }

    @Override
    public void updateCandidateNameFromDto(CandidateName newName, CandidateName existingName) {
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
    public ElectionSortDTO toElectionSortDTO(Election election) {
        if ( election == null ) {
            return null;
        }

        ElectionSortDTO electionSortDTO = new ElectionSortDTO();

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

    protected org.openapitools.model.BankDetails bankDetailsToBankDetails(BankDetails bankDetails) {
        if ( bankDetails == null ) {
            return null;
        }

        org.openapitools.model.BankDetails bankDetails1 = new org.openapitools.model.BankDetails();

        bankDetails1.setBankDetailsId( bankDetails.getBankDetailsId() );
        bankDetails1.setBankName( bankDetails.getBankName() );
        bankDetails1.setBankAddress( bankDetails.getBankAddress() );

        return bankDetails1;
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

    protected org.openapitools.model.CandidateName candidateNameToCandidateName(CandidateName candidateName) {
        if ( candidateName == null ) {
            return null;
        }

        org.openapitools.model.CandidateName candidateName1 = new org.openapitools.model.CandidateName();

        candidateName1.setFirstName( candidateName.getFirstName() );
        candidateName1.setMiddleName( candidateName.getMiddleName() );
        candidateName1.setLastName( candidateName.getLastName() );

        return candidateName1;
    }

    protected Gender genderToGender(com.ems.entities.constants.Gender gender) {
        if ( gender == null ) {
            return null;
        }

        Gender gender1;

        switch ( gender ) {
            case MALE: gender1 = Gender.MALE;
            break;
            case FEMALE: gender1 = Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }

    protected org.openapitools.model.MaritalStatus maritalStatusToMaritalStatus(MaritalStatus maritalStatus) {
        if ( maritalStatus == null ) {
            return null;
        }

        org.openapitools.model.MaritalStatus maritalStatus1;

        switch ( maritalStatus ) {
            case SINGLE: maritalStatus1 = org.openapitools.model.MaritalStatus.SINGLE;
            break;
            case MARRIED: maritalStatus1 = org.openapitools.model.MaritalStatus.MARRIED;
            break;
            case DIVORCED: maritalStatus1 = org.openapitools.model.MaritalStatus.DIVORCED;
            break;
            case WIDOWED: maritalStatus1 = org.openapitools.model.MaritalStatus.WIDOWED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + maritalStatus );
        }

        return maritalStatus1;
    }

    protected CandidateAddress candidateAddressToCandidateAddress(com.ems.entities.CandidateAddress candidateAddress) {
        if ( candidateAddress == null ) {
            return null;
        }

        CandidateAddress candidateAddress1 = new CandidateAddress();

        candidateAddress1.setAddressId( candidateAddress.getAddressId() );
        candidateAddress1.setStreet( candidateAddress.getStreet() );
        candidateAddress1.setCity( candidateAddress.getCity() );
        candidateAddress1.setZipcode( candidateAddress.getZipcode() );

        return candidateAddress1;
    }

    protected CandidateName candidateNameToCandidateName1(org.openapitools.model.CandidateName candidateName) {
        if ( candidateName == null ) {
            return null;
        }

        CandidateName candidateName1 = new CandidateName();

        candidateName1.setFirstName( candidateName.getFirstName() );
        candidateName1.setMiddleName( candidateName.getMiddleName() );
        candidateName1.setLastName( candidateName.getLastName() );

        return candidateName1;
    }

    protected com.ems.entities.constants.Gender genderToGender1(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        com.ems.entities.constants.Gender gender1;

        switch ( gender ) {
            case MALE: gender1 = com.ems.entities.constants.Gender.MALE;
            break;
            case FEMALE: gender1 = com.ems.entities.constants.Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return gender1;
    }

    protected MaritalStatus maritalStatusToMaritalStatus1(org.openapitools.model.MaritalStatus maritalStatus) {
        if ( maritalStatus == null ) {
            return null;
        }

        MaritalStatus maritalStatus1;

        switch ( maritalStatus ) {
            case SINGLE: maritalStatus1 = MaritalStatus.SINGLE;
            break;
            case MARRIED: maritalStatus1 = MaritalStatus.MARRIED;
            break;
            case DIVORCED: maritalStatus1 = MaritalStatus.DIVORCED;
            break;
            case WIDOWED: maritalStatus1 = MaritalStatus.WIDOWED;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + maritalStatus );
        }

        return maritalStatus1;
    }

    protected BankDetails bankDetailsToBankDetails1(org.openapitools.model.BankDetails bankDetails) {
        if ( bankDetails == null ) {
            return null;
        }

        BankDetails bankDetails1 = new BankDetails();

        bankDetails1.setBankDetailsId( bankDetails.getBankDetailsId() );
        bankDetails1.setBankName( bankDetails.getBankName() );
        bankDetails1.setBankAddress( bankDetails.getBankAddress() );

        return bankDetails1;
    }

    protected void candidateNameNoValidationToCandidateName(CandidateNameNoValidation candidateNameNoValidation, CandidateName mappingTarget) {
        if ( candidateNameNoValidation == null ) {
            return;
        }

        if ( candidateNameNoValidation.getFirstName() != null ) {
            mappingTarget.setFirstName( candidateNameNoValidation.getFirstName() );
        }
        if ( candidateNameNoValidation.getMiddleName() != null ) {
            mappingTarget.setMiddleName( candidateNameNoValidation.getMiddleName() );
        }
        if ( candidateNameNoValidation.getLastName() != null ) {
            mappingTarget.setLastName( candidateNameNoValidation.getLastName() );
        }
    }

    protected void bankDetailsNoValidationToBankDetails(BankDetailsNoValidation bankDetailsNoValidation, BankDetails mappingTarget) {
        if ( bankDetailsNoValidation == null ) {
            return;
        }

        if ( bankDetailsNoValidation.getBankDetailsId() != null ) {
            mappingTarget.setBankDetailsId( bankDetailsNoValidation.getBankDetailsId() );
        }
        if ( bankDetailsNoValidation.getBankName() != null ) {
            mappingTarget.setBankName( bankDetailsNoValidation.getBankName() );
        }
        if ( bankDetailsNoValidation.getBankAddress() != null ) {
            mappingTarget.setBankAddress( bankDetailsNoValidation.getBankAddress() );
        }
    }
}
