package com.ems.mappers;

import com.ems.entities.Address;
import com.ems.entities.AddressHistory;
import com.ems.entities.Audit;
import com.ems.entities.Election;
import com.ems.entities.NameHistory;
import com.ems.entities.Officers;
import com.ems.entities.Party;
import com.ems.entities.StatusHistory;
import com.ems.entities.Voter;
import com.ems.entities.VoterStatus;
import com.ems.entities.constants.AddressType;
import com.ems.entities.constants.Gender;
import com.ems.entities.constants.RoleType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.openapitools.model.AddressDTO;
import org.openapitools.model.AddressHistoryDataDTO;
import org.openapitools.model.AuditDataDTO;
import org.openapitools.model.ChangeVoterAddress;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.NameHistoryDataDTO;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.openapitools.model.StatusHistoryDataDTO;
import org.openapitools.model.TransferAddress;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-27T10:13:04+0530",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.41.0.z20250213-2037, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class GlobalMapperImpl implements GlobalMapper {

    @Override
    public Voter toVoter(VoterRegisterDTO voterRegisterDTO) {
        if ( voterRegisterDTO == null ) {
            return null;
        }

        Voter voter = new Voter();

        voter.setDateOfBirth( voterRegisterDTO.getDateOfBirth() );
        voter.setDmvNumber( voterRegisterDTO.getDmvNumber() );
        voter.setEmail( voterRegisterDTO.getEmail() );
        voter.setFirstName( voterRegisterDTO.getFirstName() );
        if ( voterRegisterDTO.getFirstVotedYear() != null ) {
            voter.setFirstVotedYear( voterRegisterDTO.getFirstVotedYear().longValue() );
        }
        voter.setGender( genderEnumToGender( voterRegisterDTO.getGender() ) );
        if ( voterRegisterDTO.getHasVotedBefore() != null ) {
            voter.setHasVotedBefore( voterRegisterDTO.getHasVotedBefore() );
        }
        voter.setLastName( voterRegisterDTO.getLastName() );
        voter.setMailingAddress( toAddress( voterRegisterDTO.getMailingAddress() ) );
        voter.setMiddleName( voterRegisterDTO.getMiddleName() );
        voter.setPhoneNumber( voterRegisterDTO.getPhoneNumber() );
        voter.setResidentialAddress( toAddress( voterRegisterDTO.getResidentialAddress() ) );
        voter.setSsnNumber( voterRegisterDTO.getSsnNumber() );
        voter.setSuffixName( voterRegisterDTO.getSuffixName() );

        return voter;
    }

    @Override
    public Voter voterDTOtoVoter(VoterUpdateRequest voterDTO, Voter voter) {
        if ( voterDTO == null ) {
            return voter;
        }

        if ( voterDTO.getDateOfBirth() != null ) {
            voter.setDateOfBirth( voterDTO.getDateOfBirth() );
        }
        if ( voterDTO.getDmvNumber() != null ) {
            voter.setDmvNumber( voterDTO.getDmvNumber() );
        }
        if ( voterDTO.getEmail() != null ) {
            voter.setEmail( voterDTO.getEmail() );
        }
        if ( voterDTO.getFirstName() != null ) {
            voter.setFirstName( voterDTO.getFirstName() );
        }
        if ( voterDTO.getFirstVotedYear() != null ) {
            voter.setFirstVotedYear( voterDTO.getFirstVotedYear().longValue() );
        }
        if ( voterDTO.getGender() != null ) {
            voter.setGender( genderEnumToGender1( voterDTO.getGender() ) );
        }
        if ( voterDTO.getHasVotedBefore() != null ) {
            voter.setHasVotedBefore( voterDTO.getHasVotedBefore() );
        }
        if ( voterDTO.getImage() != null ) {
            voter.setImage( voterDTO.getImage() );
        }
        if ( voterDTO.getLastName() != null ) {
            voter.setLastName( voterDTO.getLastName() );
        }
        if ( voterDTO.getMailingAddress() != null ) {
            if ( voter.getMailingAddress() == null ) {
                voter.setMailingAddress( new Address() );
            }
            addressDTOToAddress( voterDTO.getMailingAddress(), voter.getMailingAddress() );
        }
        if ( voterDTO.getMiddleName() != null ) {
            voter.setMiddleName( voterDTO.getMiddleName() );
        }
        if ( voterDTO.getPhoneNumber() != null ) {
            voter.setPhoneNumber( voterDTO.getPhoneNumber() );
        }
        if ( voterDTO.getResidentialAddress() != null ) {
            if ( voter.getResidentialAddress() == null ) {
                voter.setResidentialAddress( new Address() );
            }
            addressDTOToAddress( voterDTO.getResidentialAddress(), voter.getResidentialAddress() );
        }
        if ( voterDTO.getSignature() != null ) {
            voter.setSignature( voterDTO.getSignature() );
        }
        if ( voterDTO.getSsnNumber() != null ) {
            voter.setSsnNumber( voterDTO.getSsnNumber() );
        }
        if ( voterDTO.getSuffixName() != null ) {
            voter.setSuffixName( voterDTO.getSuffixName() );
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
        if ( addressDTO.getAddressType() != null ) {
            address.setAddressType( addressTypeEnumToAddressType( addressDTO.getAddressType() ) );
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
        if ( addressDTO.getTown() != null ) {
            address.setTown( addressDTO.getTown() );
        }
        if ( addressDTO.getZipCode() != null ) {
            address.setZipCode( addressDTO.getZipCode() );
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
        party.setHeadQuarters( partyDTO.getHeadQuarters() );
        party.setPartyAbbreviation( partyDTO.getPartyAbbreviation() );
        party.setPartyFoundationYear( partyDTO.getPartyFoundationYear() );
        party.setPartyFounderName( partyDTO.getPartyFounderName() );
        party.setPartyLeaderName( partyDTO.getPartyLeaderName() );
        party.setPartyName( partyDTO.getPartyName() );
        party.setPartyWebSite( partyDTO.getPartyWebSite() );

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

        if ( partyUpdateDTO.getHeadQuarters() != null ) {
            party.setHeadQuarters( partyUpdateDTO.getHeadQuarters() );
        }
        if ( partyUpdateDTO.getPartyAbbreviation() != null ) {
            party.setPartyAbbreviation( partyUpdateDTO.getPartyAbbreviation() );
        }
        if ( partyUpdateDTO.getPartyFoundationYear() != null ) {
            party.setPartyFoundationYear( partyUpdateDTO.getPartyFoundationYear() );
        }
        if ( partyUpdateDTO.getPartyFounderName() != null ) {
            party.setPartyFounderName( partyUpdateDTO.getPartyFounderName() );
        }
        if ( partyUpdateDTO.getPartyLeaderName() != null ) {
            party.setPartyLeaderName( partyUpdateDTO.getPartyLeaderName() );
        }
        if ( partyUpdateDTO.getPartyName() != null ) {
            party.setPartyName( partyUpdateDTO.getPartyName() );
        }
        if ( partyUpdateDTO.getPartySymbol() != null ) {
            party.setPartySymbol( partyUpdateDTO.getPartySymbol() );
        }
        if ( partyUpdateDTO.getPartyWebSite() != null ) {
            party.setPartyWebSite( partyUpdateDTO.getPartyWebSite() );
        }
    }

    @Override
    public Address toAddress(AddressDTO addressDTO) {
        if ( addressDTO == null ) {
            return null;
        }

        Address address = new Address();

        address.setAddressLine( addressDTO.getAddressLine() );
        address.setAddressType( addressTypeEnumToAddressType( addressDTO.getAddressType() ) );
        address.setAptNumber( addressDTO.getAptNumber() );
        address.setCity( addressDTO.getCity() );
        address.setCounty( addressDTO.getCounty() );
        address.setState( addressDTO.getState() );
        address.setTown( addressDTO.getTown() );
        address.setZipCode( addressDTO.getZipCode() );

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
        addressDTO.setState( address.getState() );
        addressDTO.setZipCode( address.getZipCode() );
        addressDTO.setAddressType( addressTypeToAddressTypeEnum( address.getAddressType() ) );
        addressDTO.setCounty( address.getCounty() );
        addressDTO.setTown( address.getTown() );

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

        election.setElectionDate( electionDTO.getElectionDate() );
        election.setElectionId( electionDTO.getElectionId() );
        election.setElectionName( electionDTO.getElectionName() );
        election.setElectionState( electionDTO.getElectionState() );
        election.setElectionType( electionDTO.getElectionType() );
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
        addressHistory.setCreatedBy( address.getCreatedBy() );
        addressHistory.setUpdatedAt( address.getUpdatedAt() );
        addressHistory.setUpdatedBy( address.getUpdatedBy() );
        addressHistory.setAddressLine( address.getAddressLine() );
        addressHistory.setAddressType( address.getAddressType() );
        addressHistory.setAptNumber( address.getAptNumber() );
        addressHistory.setCity( address.getCity() );
        addressHistory.setCounty( address.getCounty() );
        addressHistory.setState( address.getState() );
        addressHistory.setZipCode( address.getZipCode() );

        return addressHistory;
    }

    @Override
    public NameHistory toNameHistory(Voter voter) {
        if ( voter == null ) {
            return null;
        }

        NameHistory nameHistory = new NameHistory();

        nameHistory.setCreatedAt( voter.getCreatedAt() );
        nameHistory.setCreatedBy( voter.getCreatedBy() );
        nameHistory.setUpdatedAt( voter.getUpdatedAt() );
        nameHistory.setUpdatedBy( voter.getUpdatedBy() );
        nameHistory.setFirstName( voter.getFirstName() );
        nameHistory.setLastName( voter.getLastName() );
        nameHistory.setMiddleName( voter.getMiddleName() );
        nameHistory.setSuffixName( voter.getSuffixName() );
        nameHistory.setVoterId( voter.getVoterId() );

        return nameHistory;
    }

    @Override
    public Officers toRole(OfficersRegisterDTO officersRegisterDTO) {
        if ( officersRegisterDTO == null ) {
            return null;
        }

        Officers officers = new Officers();

        officers.setEmail( officersRegisterDTO.getEmail() );
        officers.setPassword( officersRegisterDTO.getPassword() );
        if ( officersRegisterDTO.getRole() != null ) {
            officers.setRole( Enum.valueOf( RoleType.class, officersRegisterDTO.getRole() ) );
        }
        officers.setSsnNumber( officersRegisterDTO.getSsnNumber() );

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

    @Override
    public List<NameHistoryDataDTO> toNameHistoryDataDTO(List<NameHistory> nameHistory) {
        if ( nameHistory == null ) {
            return null;
        }

        List<NameHistoryDataDTO> list = new ArrayList<NameHistoryDataDTO>( nameHistory.size() );
        for ( NameHistory nameHistory1 : nameHistory ) {
            list.add( nameHistoryToNameHistoryDataDTO( nameHistory1 ) );
        }

        return list;
    }

    @Override
    public List<StatusHistoryDataDTO> toStatusHistoryDataDTO(List<StatusHistory> statusHistory) {
        if ( statusHistory == null ) {
            return null;
        }

        List<StatusHistoryDataDTO> list = new ArrayList<StatusHistoryDataDTO>( statusHistory.size() );
        for ( StatusHistory statusHistory1 : statusHistory ) {
            list.add( statusHistoryToStatusHistoryDataDTO( statusHistory1 ) );
        }

        return list;
    }

    @Override
    public List<AddressHistoryDataDTO> toAddressHistoryDataDTO(List<AddressHistory> addressHistoryList) {
        if ( addressHistoryList == null ) {
            return null;
        }

        List<AddressHistoryDataDTO> list = new ArrayList<AddressHistoryDataDTO>( addressHistoryList.size() );
        for ( AddressHistory addressHistory : addressHistoryList ) {
            list.add( addressHistoryToAddressHistoryDataDTO( addressHistory ) );
        }

        return list;
    }

    @Override
    public List<AuditDataDTO> toAuditDataDTO(List<Audit> auditList) {
        if ( auditList == null ) {
            return null;
        }

        List<AuditDataDTO> list = new ArrayList<AuditDataDTO>( auditList.size() );
        for ( Audit audit : auditList ) {
            list.add( auditToAuditDataDTO( audit ) );
        }

        return list;
    }

    @Override
    public Voter voterTransferDtotoVoter(TransferAddress transferAddress, Voter voter) {
        if ( transferAddress == null ) {
            return voter;
        }

        return voter;
    }

    @Override
    public Address transferVoterAddressToAddress(TransferAddress transferAddress, Address address) {
        if ( transferAddress == null ) {
            return address;
        }

        if ( transferAddress.getAddressLine() != null ) {
            address.setAddressLine( transferAddress.getAddressLine() );
        }
        if ( transferAddress.getAddressType() != null ) {
            address.setAddressType( addressTypeEnumToAddressType1( transferAddress.getAddressType() ) );
        }
        if ( transferAddress.getAptNumber() != null ) {
            address.setAptNumber( transferAddress.getAptNumber() );
        }
        if ( transferAddress.getCity() != null ) {
            address.setCity( transferAddress.getCity() );
        }
        if ( transferAddress.getCounty() != null ) {
            address.setCounty( transferAddress.getCounty() );
        }
        if ( transferAddress.getState() != null ) {
            address.setState( transferAddress.getState() );
        }
        if ( transferAddress.getTown() != null ) {
            address.setTown( transferAddress.getTown() );
        }
        if ( transferAddress.getZipCode() != null ) {
            address.setZipCode( transferAddress.getZipCode() );
        }

        return address;
    }

    @Override
    public Voter voterDTOtoVoter(ChangeVoterAddress voterDTO, Voter voter) {
        if ( voterDTO == null ) {
            return voter;
        }

        return voter;
    }

    @Override
    public Address changeAddressDTOToAddress(ChangeVoterAddress addressDTO, Address address) {
        if ( addressDTO == null ) {
            return address;
        }

        if ( addressDTO.getAddressLine() != null ) {
            address.setAddressLine( addressDTO.getAddressLine() );
        }
        if ( addressDTO.getAddressType() != null ) {
            address.setAddressType( addressTypeEnumToAddressType2( addressDTO.getAddressType() ) );
        }
        if ( addressDTO.getAptNumber() != null ) {
            address.setAptNumber( addressDTO.getAptNumber() );
        }
        if ( addressDTO.getCity() != null ) {
            address.setCity( addressDTO.getCity() );
        }
        if ( addressDTO.getTown() != null ) {
            address.setTown( addressDTO.getTown() );
        }
        if ( addressDTO.getZipCode() != null ) {
            address.setZipCode( addressDTO.getZipCode() );
        }

        return address;
    }

    @Override
    public Voter changeVoterDTOtoVoter(ChangeVoterAddress voterDTO, Voter voter) {
        if ( voterDTO == null ) {
            return voter;
        }

        return voter;
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

    protected NameHistoryDataDTO nameHistoryToNameHistoryDataDTO(NameHistory nameHistory) {
        if ( nameHistory == null ) {
            return null;
        }

        NameHistoryDataDTO nameHistoryDataDTO = new NameHistoryDataDTO();

        if ( nameHistory.getId() != null ) {
            nameHistoryDataDTO.setId( nameHistory.getId().intValue() );
        }
        nameHistoryDataDTO.setFirstName( nameHistory.getFirstName() );
        nameHistoryDataDTO.setMiddleName( nameHistory.getMiddleName() );
        nameHistoryDataDTO.setLastName( nameHistory.getLastName() );
        nameHistoryDataDTO.setSuffixName( nameHistory.getSuffixName() );
        nameHistoryDataDTO.setCreatedAt( nameHistory.getCreatedAt() );
        nameHistoryDataDTO.setUpdatedAt( nameHistory.getUpdatedAt() );

        return nameHistoryDataDTO;
    }

    protected StatusHistoryDataDTO statusHistoryToStatusHistoryDataDTO(StatusHistory statusHistory) {
        if ( statusHistory == null ) {
            return null;
        }

        StatusHistoryDataDTO statusHistoryDataDTO = new StatusHistoryDataDTO();

        statusHistoryDataDTO.setHistoryId( statusHistory.getHistoryId() );
        statusHistoryDataDTO.setStatus( statusHistory.getStatus() );
        statusHistoryDataDTO.setCreatedAt( statusHistory.getCreatedAt() );
        statusHistoryDataDTO.setUpdatedAt( statusHistory.getUpdatedAt() );

        return statusHistoryDataDTO;
    }

    protected AddressHistoryDataDTO.AddressTypeEnum addressTypeToAddressTypeEnum1(AddressType addressType) {
        if ( addressType == null ) {
            return null;
        }

        AddressHistoryDataDTO.AddressTypeEnum addressTypeEnum;

        switch ( addressType ) {
            case RESIDENTIAL: addressTypeEnum = AddressHistoryDataDTO.AddressTypeEnum.RESIDENTIAL;
            break;
            case MAILING: addressTypeEnum = AddressHistoryDataDTO.AddressTypeEnum.MAILING;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + addressType );
        }

        return addressTypeEnum;
    }

    protected AddressHistoryDataDTO addressHistoryToAddressHistoryDataDTO(AddressHistory addressHistory) {
        if ( addressHistory == null ) {
            return null;
        }

        AddressHistoryDataDTO addressHistoryDataDTO = new AddressHistoryDataDTO();

        if ( addressHistory.getHistoryId() != null ) {
            addressHistoryDataDTO.setHistoryId( addressHistory.getHistoryId().intValue() );
        }
        addressHistoryDataDTO.setAddressLine( addressHistory.getAddressLine() );
        addressHistoryDataDTO.setAptNumber( addressHistory.getAptNumber() );
        addressHistoryDataDTO.setCity( addressHistory.getCity() );
        addressHistoryDataDTO.setCounty( addressHistory.getCounty() );
        addressHistoryDataDTO.setState( addressHistory.getState() );
        addressHistoryDataDTO.setZipCode( addressHistory.getZipCode() );
        addressHistoryDataDTO.setAddressType( addressTypeToAddressTypeEnum1( addressHistory.getAddressType() ) );
        addressHistoryDataDTO.setCreatedAt( addressHistory.getCreatedAt() );
        addressHistoryDataDTO.setUpdatedAt( addressHistory.getUpdatedAt() );

        return addressHistoryDataDTO;
    }

    protected AuditDataDTO auditToAuditDataDTO(Audit audit) {
        if ( audit == null ) {
            return null;
        }

        AuditDataDTO auditDataDTO = new AuditDataDTO();

        auditDataDTO.setId( audit.getId() );
        auditDataDTO.setTableName( audit.getTableName() );
        auditDataDTO.setVoterId( audit.getVoterId() );
        Map<String, Object> map = audit.getOldFields();
        if ( map != null ) {
            auditDataDTO.setOldFields( new LinkedHashMap<String, Object>( map ) );
        }
        Map<String, Object> map1 = audit.getChangeFields();
        if ( map1 != null ) {
            auditDataDTO.setChangeFields( new LinkedHashMap<String, Object>( map1 ) );
        }
        auditDataDTO.setCreatedBy( audit.getCreatedBy() );
        auditDataDTO.setUpdatedBy( audit.getUpdatedBy() );
        auditDataDTO.setCreatedAt( audit.getCreatedAt() );
        auditDataDTO.setUpdatedAt( audit.getUpdatedAt() );

        return auditDataDTO;
    }

    protected AddressType addressTypeEnumToAddressType1(TransferAddress.AddressTypeEnum addressTypeEnum) {
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

    protected AddressType addressTypeEnumToAddressType2(ChangeVoterAddress.AddressTypeEnum addressTypeEnum) {
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
}
