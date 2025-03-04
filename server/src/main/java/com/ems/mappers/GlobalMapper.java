package com.ems.mappers;

import com.ems.dtos.*;
import org.openapitools.model.AddressDTO;
import com.ems.entities.*;
import com.ems.entities.constants.AddressType;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(target = "voterStatus", ignore = true)
    Voter toVoter(org.openapitools.model.VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    @Mapping(target = "address", ignore = true) // Ignore address field in direct mapping
    Voter voterDTOtoVoter(org.openapitools.model.VoterDTO voterDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address addressDTOToAddress(AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    @Mapping(target = "residentialAddress",
            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.RESIDENTIAL))")
    @Mapping(target = "mailingAddress",
            expression = "java(getAddressByType(voter.getAddress(), com.ems.entities.constants.AddressType.MAILING))")
    @Mapping(source = "party.partyId", target = "partyId")
    @Mapping(target = "statusId", source = "voterStatus.statusId")
    org.openapitools.model.VoterDTO toVoterDTO(Voter voter);

    @Mapping(target = "partySymbol", ignore = true)
    Party toParty(org.openapitools.model.PartyDTO partyDTO);
    @Mapping(target = "partySymbol", ignore = true)
    org.openapitools.model.PartyDTO toPartyDTO(Party party);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "voter", ignore = true)
    Address toAddress(org.openapitools.model.AddressDTO addressDTO);

    @Mapping(source = "voter.voterId", target = "voterId")
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);

    List<org.openapitools.model.VoterStatusDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

    @Mapping(source = "address.voter.voterId", target = "voterId")
    AddressHistory toAddressHistory(Address address);

    NameHistory toNameHistory(Voter voter);

    default AddressDTO getAddressByType(List<Address> addresses, AddressType type) {
        if (addresses == null) return null;
        return addresses.stream()
                .filter(address -> address.getAddressType() == type)
                .findFirst()
                .map(this::toAddressDTO)
                .orElse(null);
    }

    Officers toRole(OfficersRegisterDTO officersRegisterDTO);
    OfficersRegisterDTO toRoleRegisterDTO(Officers officers);
    List<OfficersResponseDTO> toRoleResponseDTO(List<Officers> officers);
}
