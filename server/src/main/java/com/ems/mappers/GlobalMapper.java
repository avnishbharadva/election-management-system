package com.ems.mappers;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.PartyDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);
    Party toParty(PartyDTO partyDTO);
    PartyDTO toPartyDTO(Party party);
    Address toAddress(AddressDTO addressDTO);
    AddressDTO toAddressDTO(Address address);
    Set<Address> toAddressSet(Set<AddressDTO> addressDTOS);
    Set<AddressDTO> toAddressDTOSet(Set<Address> addressSet);
}
