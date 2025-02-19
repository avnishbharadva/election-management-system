package com.ems.mappers;

import com.ems.entities.Role;
import com.ems.dtos.*;
import com.ems.entities.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);
    Voter toVoter(VoterUpdateDTO voterUpdateDTO);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);


    Party toParty(PartyDTO partyDTO);
    PartyDTO toPartyDTO(Party party);

    Address toAddress(AddressDTO addressDTO);
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);

    Election toElectionDTO(ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);

    Role toRole(RoleRegisterDTO roleRegisterDTO);
    RoleRegisterDTO toRoleRegisterDTO(Role role);

    List<RoleResponseDTO> toRoleResponseDTO(List<Role> role);

    VoterUpdateDTO toVoterUpdateDTO(Voter voter);

    @Mapping(target = "voterId", ignore = true)
    void updateVoterFromDto(VoterUpdateDTO voterUpdateDTO, @MappingTarget Voter voter);



}
