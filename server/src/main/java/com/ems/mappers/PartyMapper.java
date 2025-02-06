package com.ems.mappers;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PartyMapper {
    Party toParty(PartyDTO partyDTO);
    PartyDTO toPartyDTO(Party party);
}
