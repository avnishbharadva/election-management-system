package com.ems.mappers;

import com.ems.dtos.*;
import org.openapitools.model.*;
import com.ems.entities.*;
import org.mapstruct.*;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.PartyDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(target = "voterStatus", ignore = true)
    Voter toVoter(org.openapitools.model.VoterRegisterDTO voterRegisterDTO);

    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterDTOtoVoter(org.openapitools.model.VoterUpdateRequest voterDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void addressDTOToAddress(AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    @Mapping(target = "partyId", source = "party.partyId")
    @Mapping(target = "statusId", source = "voterStatus.statusId")
    VoterDataDTO toVoterDTO(Voter voter);

    @Mapping(source = "partySymbol", target = "partySymbol")
    Party toParty(PartyDTO partyDTO);

    @Mapping(source = "partySymbol", target = "partySymbol")
    PartyDTO toPartyDTO(Party party);

    @Mapping(target = "addressId", ignore = true)
    Address toAddress(AddressDTO addressDTO);

    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(org.openapitools.model.ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);


    List<VoterStatusDataDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

    AddressHistory toAddressHistory(Address address);

    NameHistory toNameHistory(Voter voter);

    Officers toOfficer(OfficersRegisterDTO officersRegisterDTO);
    OfficersResponseDTO toOfficerResponseDTO(Officers officers);
    List<org.openapitools.model.OfficersResponseDTO> toOfficerResponseDTO(List<Officers> officers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterTransferDtotoVoter(TransferAddress transferAddress, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address transferVoterAddressToAddress(TransferAddress transferAddress, @MappingTarget Address address);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterDTOtoVoter(ChangeVoterAddress voterDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address changeAddressDTOToAddress(ChangeVoterAddress addressDTO, @MappingTarget Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter changeVoterDTOtoVoter(ChangeVoterAddress voterDTO, @MappingTarget Voter voter);

}