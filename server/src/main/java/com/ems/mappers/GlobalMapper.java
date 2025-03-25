package com.ems.mappers;

import com.ems.dtos.*;
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
    void addressDTOToAddress(org.openapitools.model.AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    org.openapitools.model.VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    @Mapping(target = "party", source = "party.partyName")
    @Mapping(target = "status", source = "voterStatus.statusDesc")
    org.openapitools.model.VoterDataDTO toVoterDTO(Voter voter);

    @Mapping(source = "partySymbol", target = "partySymbol")
    Party toParty(org.openapitools.model.PartyRegisterDTO partyDTO);

    @Mapping(source = "partySymbol", target = "partySymbol")
    org.openapitools.model.PartyDataDTO toPartyDTO(Party party);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partyUpdateDTOToParty(org.openapitools.model.PartyUpdateDTO partyUpdateDTO, @MappingTarget Party party);

    @Mapping(target = "addressId", ignore = true)
//    @Mapping(target = "voter", ignore = true)
    Address toAddress(org.openapitools.model.AddressDTO addressDTO);

//    @Mapping(source = "voter.voterId", target = "voterId")
    org.openapitools.model.AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<org.openapitools.model.AddressDTO> addressDTOList);
    List<org.openapitools.model.AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(org.openapitools.model.ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);


    List<org.openapitools.model.VoterStatusDataDTO> toVoterStatusDTOList(List<VoterStatus> voterStatusList);

//    @Mapping(source = "address.voter.voterId", target = "voterId")
    AddressHistory toAddressHistory(Address address);

    NameHistory toNameHistory(Voter voter);

    Officers toOfficer(org.openapitools.model.OfficersRegisterDTO officersRegisterDTO);
    org.openapitools.model.OfficersResponseDTO toOfficerResponseDTO(Officers officers);
    List<org.openapitools.model.OfficersResponseDTO> toOfficerResponseDTO(List<Officers> officers);

    List<org.openapitools.model.NameHistoryDataDTO> toNameHistoryDataDTO(List<NameHistory> nameHistory);
    List<org.openapitools.model.StatusHistoryDataDTO> toStatusHistoryDataDTO(List<StatusHistory> statusHistory);
    List<org.openapitools.model.AddressHistoryDataDTO> toAddressHistoryDataDTO(List<AddressHistory> addressHistoryList);
    List<org.openapitools.model.AuditDataDTO> toAuditDataDTO(List<Audit> auditList);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterTransferDtotoVoter(org.openapitools.model.TransferAddress transferAddress, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address transferVoterAddressToAddress(org.openapitools.model.TransferAddress transferAddress, @MappingTarget Address address);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter voterDTOtoVoter(org.openapitools.model.ChangeVoterAddress voterDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address changeAddressDTOToAddress(org.openapitools.model.ChangeVoterAddress addressDTO, @MappingTarget Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter changeVoterDTOtoVoter(org.openapitools.model.ChangeVoterAddress voterDTO, @MappingTarget Voter voter);
}