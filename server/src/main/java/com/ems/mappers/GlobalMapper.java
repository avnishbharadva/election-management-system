package com.ems.mappers;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.PartyDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GlobalMapper {
    Voter toVoter(VoterRegisterDTO voterRegisterDTO);
    Voter toVoter(VoterSearchDTO voterSearchDTO);
    VoterRegisterDTO toVoterRegisterDTO(Voter voter);

    Party toParty(PartyDTO partyDTO);
    PartyDTO toPartyDTO(Party party);

    Address toAddress(AddressDTO addressDTO);
    AddressDTO toAddressDTO(Address address);

    List<Address> toAddressList(List<AddressDTO> addressDTOList);
    List<AddressDTO> toAddressDTOList(List<Address> addressList);
}
