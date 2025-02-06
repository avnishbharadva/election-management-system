package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;

public interface PartyService {

    Party saveParty(PartyDTO partyDTO);
}
