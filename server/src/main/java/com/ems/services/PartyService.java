package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartyService {

    PartyDTO partyById(long id);
    PartyDTO saveParty(PartyDTO partyDTO);

    List<PartyDTO> findAll();
}
