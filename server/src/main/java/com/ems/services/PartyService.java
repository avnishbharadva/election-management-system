package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import org.springframework.stereotype.Service;

@Service
public interface PartyService {

    PartyDTO partyById(long id);
    Party saveParty(PartyDTO partyDTO);
}
