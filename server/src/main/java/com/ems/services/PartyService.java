package com.ems.services;


import com.ems.entities.Party;
import org.openapitools.model.PartyDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface PartyService {

    PartyDTO partyById(long id);
    PartyDTO saveParty(org.openapitools.model.PartyDTO partyDTO);
    List<PartyDTO> findAll();
}
