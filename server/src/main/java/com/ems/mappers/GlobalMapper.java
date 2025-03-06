package com.ems.mappers;

import com.ems.dtos.*;
import org.openapitools.model.AddressDTO;
import com.ems.entities.*;
import com.ems.entities.constants.AddressType;
import org.mapstruct.*;
import java.util.List;
import org.openapitools.model.VoterStatusDTO;
import org.openapitools.model.PartyDTO;
import org.openapitools.model.OfficersRegisterDTO;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(target = "voterStatus", ignore = true)
    Voter toVoter(org.openapitools.model.VoterRegisterDTO voterRegisterDTO);

    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
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

    @Mapping(source = "partySymbol", target = "partySymbol")
    Party toParty(PartyDTO partyDTO);

    @Mapping(source = "partySymbol", target = "partySymbol")
    PartyDTO toPartyDTO(Party party);

    @Mapping(target = "addressId", ignore = true)
    @Mapping(target = "voter", ignore = true)
    Address toAddress(AddressDTO addressDTO);

    @Mapping(source = "voter.voterId", target = "voterId")
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);

    Election toElectionDTO(ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);

    List<VoterStatusDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

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
    List<org.openapitools.model.OfficersResponseDTO> toRoleResponseDTO(List<Officers> officers);
}
