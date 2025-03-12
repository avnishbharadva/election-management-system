package com.ems.mappers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.entities.AddressHistory;
import com.ems.entities.Election;
import com.ems.entities.NameHistory;
import com.ems.entities.Officers;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.entities.VoterStatus;
import com.ems.entities.constants.AddressType;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.RoleType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.openapitools.model.AddressDTO;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-12T17:41:32+0530",
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
        voter.setGender( genderEnumToGender( voterRegisterDTO.getGender() ) );
        voter.setDmvNumber( voterRegisterDTO.getDmvNumber() );
        voter.setSsnNumber( voterRegisterDTO.getSsnNumber() );
        voter.setEmail( voterRegisterDTO.getEmail() );
        voter.setPhoneNumber( voterRegisterDTO.getPhoneNumber() );
        if ( voterRegisterDTO.getHasVotedBefore() != null ) {
            voter.setHasVotedBefore( voterRegisterDTO.getHasVotedBefore() );
        }
        if ( voterRegisterDTO.getFirstVotedYear() != null ) {
            voter.setFirstVotedYear( voterRegisterDTO.getFirstVotedYear().longValue() );
        }
        voter.setResidentialAddress( toAddress( voterRegisterDTO.getResidentialAddress() ) );
        voter.setMailingAddress( toAddress( voterRegisterDTO.getMailingAddress() ) );

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
    public Voter voterDTOtoVoter(VoterUpdateRequest voterDTO, Voter voter) {
        if ( voterDTO == null ) {
            return voter;
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
            voter.setGender( genderEnumToGender1( voterDTO.getGender() ) );
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
            voter.setFirstVotedYear( voterDTO.getFirstVotedYear().longValue() );
        }
        if ( voterDTO.getResidentialAddress() != null ) {
            if ( voter.getResidentialAddress() == null ) {
                voter.setResidentialAddress( new Address() );
            }
            addressDTOToAddress( voterDTO.getResidentialAddress(), voter.getResidentialAddress() );
        }
        if ( voterDTO.getMailingAddress() != null ) {
            if ( voter.getMailingAddress() == null ) {
                voter.setMailingAddress( new Address() );
            }
            addressDTOToAddress( voterDTO.getMailingAddress(), voter.getMailingAddress() );
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
            address.setAddressType( addressTypeEnumToAddressType( addressDTO.getAddressType() ) );
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
        voterRegisterDTO.setGender( genderToGenderEnum( voter.getGender() ) );
        voterRegisterDTO.setDmvNumber( voter.getDmvNumber() );
        voterRegisterDTO.setSsnNumber( voter.getSsnNumber() );
        voterRegisterDTO.setEmail( voter.getEmail() );
        voterRegisterDTO.setPhoneNumber( voter.getPhoneNumber() );
        voterRegisterDTO.setHasVotedBefore( voter.isHasVotedBefore() );
        if ( voter.getFirstVotedYear() != null ) {
            voterRegisterDTO.setFirstVotedYear( voter.getFirstVotedYear().intValue() );
        }
        voterRegisterDTO.setResidentialAddress( toAddressDTO( voter.getResidentialAddress() ) );
        voterRegisterDTO.setMailingAddress( toAddressDTO( voter.getMailingAddress() ) );

        return voterRegisterDTO;
    }

    @Override
    public VoterDataDTO toVoterDTO(Voter voter) {
        if ( voter == null ) {
            return null;
        }

        VoterDataDTO voterDataDTO = new VoterDataDTO();

        voterDataDTO.setParty( voterPartyPartyName( voter ) );
        voterDataDTO.setStatus( voterVoterStatusStatusDesc( voter ) );
        voterDataDTO.setVoterId( voter.getVoterId() );
        voterDataDTO.setFirstName( voter.getFirstName() );
        voterDataDTO.setMiddleName( voter.getMiddleName() );
        voterDataDTO.setLastName( voter.getLastName() );
        voterDataDTO.setSuffixName( voter.getSuffixName() );
        voterDataDTO.setDateOfBirth( voter.getDateOfBirth() );
        voterDataDTO.setGender( genderToGenderEnum1( voter.getGender() ) );
        voterDataDTO.setDmvNumber( voter.getDmvNumber() );
        voterDataDTO.setSsnNumber( voter.getSsnNumber() );
        voterDataDTO.setEmail( voter.getEmail() );
        voterDataDTO.setPhoneNumber( voter.getPhoneNumber() );
        voterDataDTO.setHasVotedBefore( voter.isHasVotedBefore() );
        if ( voter.getFirstVotedYear() != null ) {
            voterDataDTO.setFirstVotedYear( voter.getFirstVotedYear().intValue() );
        }
        voterDataDTO.setResidentialAddress( toAddressDTO( voter.getResidentialAddress() ) );
        voterDataDTO.setMailingAddress( toAddressDTO( voter.getMailingAddress() ) );
        voterDataDTO.setImage( voter.getImage() );
        voterDataDTO.setSignature( voter.getSignature() );

        return voterDataDTO;
    }

    @Override
    public Party toParty(PartyRegisterDTO partyDTO) {
        if ( partyDTO == null ) {
            return null;
        }

        Party party = new Party();

        party.setPartySymbol( partyDTO.getPartySymbol() );
        party.setPartyName( partyDTO.getPartyName() );
        party.setPartyAbbreviation( partyDTO.getPartyAbbreviation() );
        party.setPartyFoundationYear( partyDTO.getPartyFoundationYear() );
        party.setPartyWebSite( partyDTO.getPartyWebSite() );
        party.setHeadQuarters( partyDTO.getHeadQuarters() );
        party.setPartyFounderName( partyDTO.getPartyFounderName() );
        party.setPartyLeaderName( partyDTO.getPartyLeaderName() );

        return party;
    }

    @Override
    public PartyDataDTO toPartyDTO(Party party) {
        if ( party == null ) {
            return null;
        }

        PartyDataDTO partyDataDTO = new PartyDataDTO();

        partyDataDTO.setPartySymbol( party.getPartySymbol() );
        partyDataDTO.setPartyName( party.getPartyName() );
        partyDataDTO.setPartyAbbreviation( party.getPartyAbbreviation() );
        partyDataDTO.setPartyFounderName( party.getPartyFounderName() );
        partyDataDTO.setPartyFoundationYear( party.getPartyFoundationYear() );
        partyDataDTO.setPartyLeaderName( party.getPartyLeaderName() );
        partyDataDTO.setPartyWebSite( party.getPartyWebSite() );
        partyDataDTO.setHeadQuarters( party.getHeadQuarters() );
        partyDataDTO.setPartyId( party.getPartyId() );

        return partyDataDTO;
    }

    @Override
    public void partyUpdateDTOToParty(PartyUpdateDTO partyUpdateDTO, Party party) {
        if ( partyUpdateDTO == null ) {
            return;
        }

        if ( partyUpdateDTO.getPartyName() != null ) {
            party.setPartyName( partyUpdateDTO.getPartyName() );
        }
        if ( partyUpdateDTO.getPartyAbbreviation() != null ) {
            party.setPartyAbbreviation( partyUpdateDTO.getPartyAbbreviation() );
        }
        if ( partyUpdateDTO.getPartySymbol() != null ) {
            party.setPartySymbol( partyUpdateDTO.getPartySymbol() );
        }
        if ( partyUpdateDTO.getPartyFoundationYear() != null ) {
            party.setPartyFoundationYear( partyUpdateDTO.getPartyFoundationYear() );
        }
        if ( partyUpdateDTO.getPartyWebSite() != null ) {
            party.setPartyWebSite( partyUpdateDTO.getPartyWebSite() );
        }
        if ( partyUpdateDTO.getHeadQuarters() != null ) {
            party.setHeadQuarters( partyUpdateDTO.getHeadQuarters() );
        }
        if ( partyUpdateDTO.getPartyFounderName() != null ) {
            party.setPartyFounderName( partyUpdateDTO.getPartyFounderName() );
        }
        if ( partyUpdateDTO.getPartyLeaderName() != null ) {
            party.setPartyLeaderName( partyUpdateDTO.getPartyLeaderName() );
        }
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
        address.setState( addressDTO.getState() );
        address.setZipCode( addressDTO.getZipCode() );
        address.setAddressType( addressTypeEnumToAddressType( addressDTO.getAddressType() ) );

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
        addressDTO.setState( address.getState() );
        addressDTO.setZipCode( address.getZipCode() );
        addressDTO.setAddressType( addressTypeToAddressTypeEnum( address.getAddressType() ) );

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
        if ( electionDTO.getTotalSeats() != null ) {
            election.setTotalSeats( electionDTO.getTotalSeats() );
        }

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

    @Override
    public List<VoterStatusDataDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList) {
        if ( voterStatusList == null ) {
            return null;
        }

        List<VoterStatusDataDTO> list = new ArrayList<VoterStatusDataDTO>( voterStatusList.size() );
        for ( VoterStatus voterStatus : voterStatusList ) {
            list.add( voterStatusToVoterStatusDataDTO( voterStatus ) );
        }

        return list;
    }

    @Override
    public AddressHistory toAddressHistory(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressHistory addressHistory = new AddressHistory();

        addressHistory.setCreatedAt( address.getCreatedAt() );
        addressHistory.setUpdatedAt( address.getUpdatedAt() );
        addressHistory.setAddressLine( address.getAddressLine() );
        addressHistory.setAptNumber( address.getAptNumber() );
        addressHistory.setCity( address.getCity() );
        addressHistory.setCounty( address.getCounty() );
        addressHistory.setState( address.getState() );
        addressHistory.setZipCode( address.getZipCode() );
        addressHistory.setAddressType( address.getAddressType() );

        return addressHistory;
    }

    @Override
    public NameHistory toNameHistory(Voter voter) {
        if ( voter == null ) {
            return null;
        }

        NameHistory nameHistory = new NameHistory();

        nameHistory.setCreatedAt( voter.getCreatedAt() );
        nameHistory.setUpdatedAt( voter.getUpdatedAt() );
        nameHistory.setVoterId( voter.getVoterId() );
        nameHistory.setFirstName( voter.getFirstName() );
        nameHistory.setMiddleName( voter.getMiddleName() );
        nameHistory.setLastName( voter.getLastName() );
        nameHistory.setSuffixName( voter.getSuffixName() );

        return nameHistory;
    }

    @Override
    public Officers toRole(OfficersRegisterDTO officersRegisterDTO) {
        if ( officersRegisterDTO == null ) {
            return null;
        }

        Officers officers = new Officers();

        officers.setSsnNumber( officersRegisterDTO.getSsnNumber() );
        if ( officersRegisterDTO.getRole() != null ) {
            officers.setRole( Enum.valueOf( RoleType.class, officersRegisterDTO.getRole() ) );
        }
        officers.setPassword( officersRegisterDTO.getPassword() );
        officers.setEmail( officersRegisterDTO.getEmail() );

        return officers;
    }

    @Override
    public OfficersRegisterDTO toRoleRegisterDTO(Officers officers) {
        if ( officers == null ) {
            return null;
        }

        OfficersRegisterDTO officersRegisterDTO = new OfficersRegisterDTO();

        officersRegisterDTO.setSsnNumber( officers.getSsnNumber() );
        if ( officers.getRole() != null ) {
            officersRegisterDTO.setRole( officers.getRole().name() );
        }
        officersRegisterDTO.setEmail( officers.getEmail() );
        officersRegisterDTO.setPassword( officers.getPassword() );

        return officersRegisterDTO;
    }

    @Override
    public List<OfficersResponseDTO> toRoleResponseDTO(List<Officers> officers) {
        if ( officers == null ) {
            return null;
        }

        List<OfficersResponseDTO> list = new ArrayList<OfficersResponseDTO>( officers.size() );
        for ( Officers officers1 : officers ) {
            list.add( officersToOfficersResponseDTO( officers1 ) );
        }

        return list;
    }

    protected Gender genderEnumToGender(VoterRegisterDTO.GenderEnum genderEnum) {
        if ( genderEnum == null ) {
            return null;
        }

        Gender gender;

        switch ( genderEnum ) {
            case MALE: gender = Gender.MALE;
            break;
            case FEMALE: gender = Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + genderEnum );
        }

        return gender;
    }

    protected Gender genderEnumToGender1(VoterUpdateRequest.GenderEnum genderEnum) {
        if ( genderEnum == null ) {
            return null;
        }

        Gender gender;

        switch ( genderEnum ) {
            case MALE: gender = Gender.MALE;
            break;
            case FEMALE: gender = Gender.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + genderEnum );
        }

        return gender;
    }

    protected AddressType addressTypeEnumToAddressType(AddressDTO.AddressTypeEnum addressTypeEnum) {
        if ( addressTypeEnum == null ) {
            return null;
        }

        AddressType addressType;

        switch ( addressTypeEnum ) {
            case RESIDENTIAL: addressType = AddressType.RESIDENTIAL;
            break;
            case MAILING: addressType = AddressType.MAILING;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + addressTypeEnum );
        }

        return addressType;
    }

    private Long voterPartyPartyId(Voter voter) {
        Party party = voter.getParty();
        if ( party == null ) {
            return null;
        }
        return party.getPartyId();
    }

    protected VoterRegisterDTO.GenderEnum genderToGenderEnum(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        VoterRegisterDTO.GenderEnum genderEnum;

        switch ( gender ) {
            case MALE: genderEnum = VoterRegisterDTO.GenderEnum.MALE;
            break;
            case FEMALE: genderEnum = VoterRegisterDTO.GenderEnum.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return genderEnum;
    }

    private String voterPartyPartyName(Voter voter) {
        Party party = voter.getParty();
        if ( party == null ) {
            return null;
        }
        return party.getPartyName();
    }

    private String voterVoterStatusStatusDesc(Voter voter) {
        VoterStatus voterStatus = voter.getVoterStatus();
        if ( voterStatus == null ) {
            return null;
        }
        return voterStatus.getStatusDesc();
    }

    protected VoterDataDTO.GenderEnum genderToGenderEnum1(Gender gender) {
        if ( gender == null ) {
            return null;
        }

        VoterDataDTO.GenderEnum genderEnum;

        switch ( gender ) {
            case MALE: genderEnum = VoterDataDTO.GenderEnum.MALE;
            break;
            case FEMALE: genderEnum = VoterDataDTO.GenderEnum.FEMALE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + gender );
        }

        return genderEnum;
    }

    protected AddressDTO.AddressTypeEnum addressTypeToAddressTypeEnum(AddressType addressType) {
        if ( addressType == null ) {
            return null;
        }

        AddressDTO.AddressTypeEnum addressTypeEnum;

        switch ( addressType ) {
            case RESIDENTIAL: addressTypeEnum = AddressDTO.AddressTypeEnum.RESIDENTIAL;
            break;
            case MAILING: addressTypeEnum = AddressDTO.AddressTypeEnum.MAILING;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + addressType );
        }

        return addressTypeEnum;
    }

    protected VoterStatusDataDTO voterStatusToVoterStatusDataDTO(VoterStatus voterStatus) {
        if ( voterStatus == null ) {
            return null;
        }

        VoterStatusDataDTO voterStatusDataDTO = new VoterStatusDataDTO();

        voterStatusDataDTO.setStatusId( voterStatus.getStatusId() );
        voterStatusDataDTO.setStatusDesc( voterStatus.getStatusDesc() );

        return voterStatusDataDTO;
    }

    protected OfficersResponseDTO officersToOfficersResponseDTO(Officers officers) {
        if ( officers == null ) {
            return null;
        }

        OfficersResponseDTO officersResponseDTO = new OfficersResponseDTO();

        officersResponseDTO.setOfficerId( officers.getOfficerId() );
        officersResponseDTO.setSsnNumber( officers.getSsnNumber() );
        if ( officers.getRole() != null ) {
            officersResponseDTO.setRole( officers.getRole().name() );
        }
        officersResponseDTO.setEmail( officers.getEmail() );
        officersResponseDTO.setPassword( officers.getPassword() );

        return officersResponseDTO;
    }
}
