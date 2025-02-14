package com.ems.mappers;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.ElectionDTO;
import com.ems.dtos.PartyDTO;
import com.ems.dtos.VoterDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
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
    date = "2025-02-14T13:31:06+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
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
        if ( voterRegisterDTO.getHasVotedBefore() != null ) {
            voter.setHasVotedBefore( voterRegisterDTO.getHasVotedBefore() );
        }
        voter.setFirstVotedYear( voterRegisterDTO.getFirstVotedYear() );
        voter.setAddress( toAddressList( voterRegisterDTO.getAddress() ) );

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
    public Voter voterDTOtoVoter(VoterDTO voterDTO, Voter voter) {
        if ( voterDTO == null ) {
            return voter;
        }

        if ( voterDTO.getVoterId() != null ) {
            voter.setVoterId( voterDTO.getVoterId() );
        }
        if ( voterDTO.getFirstName() != null ) {
            voter.setFirstName( voterDTO.getFirstName() );
        }
        if ( voterDTO.getMiddleName() != null ) {
            voter.setMiddleName( voterDTO.getMiddleName() );
        }
        if ( voterDTO.getLastName() != null ) {
            voter.setLastName( voterDTO.getLastName() );
        }
        if ( voterDTO.getSuffixName() != null ) {
            voter.setSuffixName( voterDTO.getSuffixName() );
        }
        if ( voterDTO.getDateOfBirth() != null ) {
            voter.setDateOfBirth( voterDTO.getDateOfBirth() );
        }
        if ( voterDTO.getGender() != null ) {
            voter.setGender( voterDTO.getGender() );
        }
        if ( voterDTO.getDmvNumber() != null ) {
            voter.setDmvNumber( voterDTO.getDmvNumber() );
        }
        if ( voterDTO.getSsnNumber() != null ) {
            voter.setSsnNumber( voterDTO.getSsnNumber() );
        }
        if ( voterDTO.getEmail() != null ) {
            voter.setEmail( voterDTO.getEmail() );
        }
        if ( voterDTO.getPhoneNumber() != null ) {
            voter.setPhoneNumber( voterDTO.getPhoneNumber() );
        }
        if ( voterDTO.getHasVotedBefore() != null ) {
            voter.setHasVotedBefore( voterDTO.getHasVotedBefore() );
        }
        if ( voterDTO.getFirstVotedYear() != null ) {
            voter.setFirstVotedYear( voterDTO.getFirstVotedYear() );
        }
        if ( voterDTO.getImage() != null ) {
            voter.setImage( voterDTO.getImage() );
        }
        if ( voterDTO.getSignature() != null ) {
            voter.setSignature( voterDTO.getSignature() );
        }

        return voter;
    }

    @Override
    public Address addressDTOToAddress(AddressDTO addressDTO, Address address) {
        if ( addressDTO == null ) {
            return address;
        }

        if ( addressDTO.getAddressId() != null ) {
            address.setAddressId( addressDTO.getAddressId() );
        }
        if ( addressDTO.getAddressLine() != null ) {
            address.setAddressLine( addressDTO.getAddressLine() );
        }
        if ( addressDTO.getAptNumber() != null ) {
            address.setAptNumber( addressDTO.getAptNumber() );
        }
        if ( addressDTO.getCity() != null ) {
            address.setCity( addressDTO.getCity() );
        }
        if ( addressDTO.getCounty() != null ) {
            address.setCounty( addressDTO.getCounty() );
        }
        if ( addressDTO.getState() != null ) {
            address.setState( addressDTO.getState() );
        }
        if ( addressDTO.getZipCode() != null ) {
            address.setZipCode( addressDTO.getZipCode() );
        }
        if ( addressDTO.getAddressType() != null ) {
            address.setAddressType( addressDTO.getAddressType() );
        }

        return address;
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
        voterRegisterDTO.setHasVotedBefore( voter.isHasVotedBefore() );
        voterRegisterDTO.setFirstVotedYear( voter.getFirstVotedYear() );
        voterRegisterDTO.setAddress( toAddressDTOList( voter.getAddress() ) );

        return voterRegisterDTO;
    }

    @Override
    public VoterDTO toVoterDTO(Voter voter) {
        if ( voter == null ) {
            return null;
        }

        VoterDTO voterDTO = new VoterDTO();

        voterDTO.setVoterId( voter.getVoterId() );
        voterDTO.setFirstName( voter.getFirstName() );
        voterDTO.setMiddleName( voter.getMiddleName() );
        voterDTO.setLastName( voter.getLastName() );
        voterDTO.setSuffixName( voter.getSuffixName() );
        voterDTO.setDateOfBirth( voter.getDateOfBirth() );
        voterDTO.setGender( voter.getGender() );
        voterDTO.setDmvNumber( voter.getDmvNumber() );
        voterDTO.setSsnNumber( voter.getSsnNumber() );
        voterDTO.setEmail( voter.getEmail() );
        voterDTO.setPhoneNumber( voter.getPhoneNumber() );
        voterDTO.setHasVotedBefore( voter.isHasVotedBefore() );
        voterDTO.setFirstVotedYear( voter.getFirstVotedYear() );
        voterDTO.setImage( voter.getImage() );
        voterDTO.setSignature( voter.getSignature() );

        return voterDTO;
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

        address.setAddressId( addressDTO.getAddressId() );
        address.setAddressLine( addressDTO.getAddressLine() );
        address.setAptNumber( addressDTO.getAptNumber() );
        address.setCity( addressDTO.getCity() );
        address.setCounty( addressDTO.getCounty() );
        address.setState( addressDTO.getState() );
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

        addressDTO.setAddressId( address.getAddressId() );
        addressDTO.setAddressLine( address.getAddressLine() );
        addressDTO.setAptNumber( address.getAptNumber() );
        addressDTO.setCity( address.getCity() );
        addressDTO.setCounty( address.getCounty() );
        addressDTO.setState( address.getState() );
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
