package com.ems.services;

import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface PartyService {

    PartyDataDTO partyById(long id);
    PartyDataDTO saveParty(PartyDataDTO partyDTO);
    List<PartyDataDTO> findAll();
    PartyDataDTO updateParty(Long partyId, PartyUpdateDTO partyUpdateDTO);
    void deleteParty(Long partyId);

}
