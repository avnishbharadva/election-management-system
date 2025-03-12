package com.ems.mappers;

import com.ems.dtos.VoterSearchDTO;
import org.openapitools.model.*;
import com.ems.entities.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(target = "voterStatus", ignore = true)
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);

    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterDTOtoVoter(VoterUpdateRequest voterDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address addressDTOToAddress(AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

//    @Mapping(target = "data.residentialAddress",
//            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.RESIDENTIAL))")
//    @Mapping(target = "data.mailingAddress",
//            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.MAILING))")

//    @Mapping(target = "data", source = ".")
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "data.partyId", source = "party.partyId")
//    @Mapping(target = "data.statusId", source = "voterStatus.statusId")
//    org.openapitools.model.VoterDTO toVoterDTO(Voter voter);

    @Mapping(target = "party", source = "party.partyName")
    @Mapping(target = "status", source = "voterStatus.statusDesc")
    VoterDataDTO toVoterDTO(Voter voter);

    @Mapping(source = "partySymbol", target = "partySymbol")
    Party toParty(PartyRegisterDTO partyDTO);

    @Mapping(source = "partySymbol", target = "partySymbol")
    PartyDataDTO toPartyDTO(Party party);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partyUpdateDTOToParty(PartyUpdateDTO partyUpdateDTO, @MappingTarget Party party);

    @Mapping(target = "addressId", ignore = true)
//    @Mapping(target = "voter", ignore = true)
    Address toAddress(AddressDTO addressDTO);

//    @Mapping(source = "voter.voterId", target = "voterId")
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(org.openapitools.model.ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);


    List<VoterStatusDataDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

//    @Mapping(source = "address.voter.voterId", target = "voterId")
    AddressHistory toAddressHistory(Address address);

    NameHistory toNameHistory(Voter voter);

//    default AddressDTO getAddressByType(List<Address> addresses, AddressType type) {
//        if (addresses == null) return null;
//        return addresses.stream()
//                .filter(address -> address.getAddressType() == type)
//                .findFirst()
//                .map(this::toAddressDTO)
//                .orElse(null);
//    }

    Officers toRole(OfficersRegisterDTO officersRegisterDTO);
    OfficersRegisterDTO toRoleRegisterDTO(Officers officers);
    List<org.openapitools.model.OfficersResponseDTO> toRoleResponseDTO(List<Officers> officers);
}