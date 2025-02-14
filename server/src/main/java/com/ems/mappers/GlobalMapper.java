package com.ems.mappers;

import com.ems.dtos.*;
import com.ems.entities.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter updateDTOtoVoter(VoterResponseDTO voterUpdateDTO, @MappingTarget Voter voter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address updateAddressToAddress(AddressDTO addressDTO, @MappingTarget Address address);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "signature", ignore = true)
    @Mapping(source = "party.partyId", target = "partyId")
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    VoterResponseDTO toVoterResponseDTO(Voter voter);

    Party toParty(PartyDTO partyDTO);
    PartyDTO toPartyDTO(Party party);

    Address toAddress(AddressDTO addressDTO);
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);


    Election toElectionDTO(ElectionDTO electionDTO);
    ElectionDTO toElection(Election election);
}
