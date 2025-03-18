package com.ems.mappers;

import com.ems.dtos.VoterSearchDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.AddressDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.NameHistoryDataDTO;
import org.openapitools.model.StatusHistoryDataDTO;
import org.openapitools.model.AddressHistoryDataDTO;
import org.openapitools.model.AuditDataDTO;
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

    Officers toRole(OfficersRegisterDTO officersRegisterDTO);
    OfficersRegisterDTO toRoleRegisterDTO(Officers officers);
    List<org.openapitools.model.OfficersResponseDTO> toRoleResponseDTO(List<Officers> officers);

    List<NameHistoryDataDTO> toNameHistoryDataDTO(List<NameHistory> nameHistory);
    List<StatusHistoryDataDTO> toStatusHistoryDataDTO(List<StatusHistory> statusHistory);
    List<AddressHistoryDataDTO> toAddressHistoryDataDTO(List<AddressHistory> addressHistoryList);
    List<AuditDataDTO> toAuditDataDTO(List<Audit> auditList);
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