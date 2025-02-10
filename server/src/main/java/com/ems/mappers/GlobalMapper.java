package com.ems.mappers;

import com.ems.dtos.*;
import com.ems.entities.Address;
import com.ems.entities.Election;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);

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
}
