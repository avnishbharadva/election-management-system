package com.ems.mappers;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyMapper {

    PartyDTO toPartyDTO(Party party);
    Party toParty(PartyDTO partyDTO);
}
