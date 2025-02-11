package com.ems.mappers;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.ElectionDTO;
import com.ems.dtos.PartyDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.entities.Address;
import com.ems.entities.Election;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-11T12:43:30+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class GlobalMapperImpl implements GlobalMapper {

    @Override
    public Voter toVoter(VoterRegisterDTO voterRegisterDTO) {
        if ( voterRegisterDTO == null ) {
            return null;
        }

        Voter voter = new Voter();

        voter.setFirstName( voterRegisterDTO.getFirstName() );
        voter.setMiddleName( voterRegisterDTO.getMiddleName() );
        voter.setLastName( voterRegisterDTO.getLastName() );
        voter.setSuffixName( voterRegisterDTO.getSuffixName() );
        voter.setDateOfBirth( voterRegisterDTO.getDateOfBirth() );
        voter.setGender( voterRegisterDTO.getGender() );
        voter.setDmvNumber( voterRegisterDTO.getDmvNumber() );
        voter.setSsnNumber( voterRegisterDTO.getSsnNumber() );
        voter.setEmail( voterRegisterDTO.getEmail() );
        voter.setPhoneNumber( voterRegisterDTO.getPhoneNumber() );
        voter.setHasVotedBefore( voterRegisterDTO.getHasVotedBefore() );
        voter.setFirstVotedYear( voterRegisterDTO.getFirstVotedYear() );
        voter.setAddress( toAddressList( voterRegisterDTO.getAddress() ) );
        voter.setImage( voterRegisterDTO.getImage() );
        voter.setSignature( voterRegisterDTO.getSignature() );

        return voter;
    }

    @Override
    public Voter toVoter(VoterSearchDTO voterSearchDTO) {
        if ( voterSearchDTO == null ) {
            return null;
        }

        Voter voter = new Voter();

        voter.setFirstName( voterSearchDTO.getFirstName() );
        voter.setLastName( voterSearchDTO.getLastName() );
        voter.setDateOfBirth( voterSearchDTO.getDateOfBirth() );
        voter.setDmvNumber( voterSearchDTO.getDmvNumber() );
        voter.setSsnNumber( voterSearchDTO.getSsnNumber() );

        return voter;
    }

    @Override
    public Voter toVoter(VoterUpdateDTO voterUpdateDTO) {
        if ( voterUpdateDTO == null ) {
            return null;
        }

        Voter voter = new Voter();

        voter.setFirstName( voterUpdateDTO.getFirstName() );
        voter.setMiddleName( voterUpdateDTO.getMiddleName() );
        voter.setLastName( voterUpdateDTO.getLastName() );
        voter.setSuffixName( voterUpdateDTO.getSuffixName() );
        voter.setDateOfBirth( voterUpdateDTO.getDateOfBirth() );
        voter.setGender( voterUpdateDTO.getGender() );
        voter.setEmail( voterUpdateDTO.getEmail() );
        voter.setPhoneNumber( voterUpdateDTO.getPhoneNumber() );
        voter.setHasVotedBefore( voterUpdateDTO.getHasVotedBefore() );
        voter.setFirstVotedYear( voterUpdateDTO.getFirstVotedYear() );
        voter.setImage( voterUpdateDTO.getImage() );
        voter.setSignature( voterUpdateDTO.getSignature() );

        return voter;
    }

    @Override
    public VoterRegisterDTO toVoterRegisterDTO(Voter voter) {
        if ( voter == null ) {
            return null;
        }

        VoterRegisterDTO voterRegisterDTO = new VoterRegisterDTO();

        voterRegisterDTO.setPartyId( voterPartyPartyId( voter ) );
        voterRegisterDTO.setFirstName( voter.getFirstName() );
        voterRegisterDTO.setMiddleName( voter.getMiddleName() );
        voterRegisterDTO.setLastName( voter.getLastName() );
        voterRegisterDTO.setSuffixName( voter.getSuffixName() );
        voterRegisterDTO.setDateOfBirth( voter.getDateOfBirth() );
        voterRegisterDTO.setGender( voter.getGender() );
        voterRegisterDTO.setDmvNumber( voter.getDmvNumber() );
        voterRegisterDTO.setSsnNumber( voter.getSsnNumber() );
        voterRegisterDTO.setEmail( voter.getEmail() );
        voterRegisterDTO.setPhoneNumber( voter.getPhoneNumber() );
        voterRegisterDTO.setHasVotedBefore( voter.getHasVotedBefore() );
        voterRegisterDTO.setFirstVotedYear( voter.getFirstVotedYear() );
        voterRegisterDTO.setAddress( toAddressDTOList( voter.getAddress() ) );
        voterRegisterDTO.setImage( voter.getImage() );
        voterRegisterDTO.setSignature( voter.getSignature() );

        return voterRegisterDTO;
    }

    @Override
    public Party toParty(PartyDTO partyDTO) {
        if ( partyDTO == null ) {
            return null;
        }

        Party party = new Party();

        party.setPartyId( partyDTO.getPartyId() );
        party.setPartyName( partyDTO.getPartyName() );
        party.setPartyAbbreviation( partyDTO.getPartyAbbreviation() );
        party.setPartySymbol( partyDTO.getPartySymbol() );
        party.setPartyFoundationYear( partyDTO.getPartyFoundationYear() );
        party.setPartyWebSite( partyDTO.getPartyWebSite() );
        party.setHeadQuarters( partyDTO.getHeadQuarters() );
        party.setFounderName( partyDTO.getFounderName() );

        return party;
    }

    @Override
    public PartyDTO toPartyDTO(Party party) {
        if ( party == null ) {
            return null;
        }

        PartyDTO partyDTO = new PartyDTO();

        partyDTO.setPartyId( party.getPartyId() );
        partyDTO.setPartyName( party.getPartyName() );
        partyDTO.setPartyAbbreviation( party.getPartyAbbreviation() );
        partyDTO.setPartySymbol( party.getPartySymbol() );
        partyDTO.setPartyFoundationYear( party.getPartyFoundationYear() );
        partyDTO.setPartyWebSite( party.getPartyWebSite() );
        partyDTO.setHeadQuarters( party.getHeadQuarters() );
        partyDTO.setFounderName( party.getFounderName() );

        return partyDTO;
    }

    @Override
    public Address toAddress(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setAddressLine( addressDTO.getAddressLine() );
        address.setAptNumber( addressDTO.getAptNumber() );
        address.setCity( addressDTO.getCity() );
        address.setCounty( addressDTO.getCounty() );
        address.setZipCode( addressDTO.getZipCode() );
        address.setAddressType( addressDTO.getAddressType() );

        return address;
    }

    @Override
    public AddressDTO toAddressDTO(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressLine( address.getAddressLine() );
        addressDTO.setAptNumber( address.getAptNumber() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setCounty( address.getCounty() );
        addressDTO.setZipCode( address.getZipCode() );
        addressDTO.setAddressType( address.getAddressType() );

        return addressDTO;
    }

    @Override
    public List<Address> toAddressList(List<AddressDTO> addressDTOList) {
        if ( addressDTOList == null ) {
            return null;
        }

        List<Address> list = new ArrayList<Address>( addressDTOList.size() );
        for ( AddressDTO addressDTO : addressDTOList ) {
            list.add( toAddress( addressDTO ) );
        }

        return list;
    }

    @Override
    public List<AddressDTO> toAddressDTOList(List<Address> addressList) {
        if ( addressList == null ) {
            return null;
        }

        List<AddressDTO> list = new ArrayList<AddressDTO>( addressList.size() );
        for ( Address address : addressList ) {
            list.add( toAddressDTO( address ) );
        }

        return list;
    }

    @Override
    public Election toElectionDTO(ElectionDTO electionDTO) {
        if ( electionDTO == null ) {
            return null;
        }

        Election election = new Election();

        election.setElectionId( electionDTO.getElectionId() );
        election.setElectionName( electionDTO.getElectionName() );
        election.setElectionType( electionDTO.getElectionType() );
        election.setElectionDate( electionDTO.getElectionDate() );
        election.setElectionState( electionDTO.getElectionState() );
        election.setTotalSeats( electionDTO.getTotalSeats() );

        return election;
    }

    @Override
    public ElectionDTO toElection(Election election) {
        if ( election == null ) {
            return null;
        }

        ElectionDTO electionDTO = new ElectionDTO();

        electionDTO.setElectionId( election.getElectionId() );
        electionDTO.setElectionName( election.getElectionName() );
        electionDTO.setElectionType( election.getElectionType() );
        electionDTO.setElectionDate( election.getElectionDate() );
        electionDTO.setElectionState( election.getElectionState() );
        electionDTO.setTotalSeats( election.getTotalSeats() );

        return electionDTO;
    }

    private Long voterPartyPartyId(Voter voter) {
        Party party = voter.getParty();
        if ( party == null ) {
            return null;
        }
        return party.getPartyId();
    }
}
