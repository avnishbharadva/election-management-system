package com.ems.services;

<<<<<<< HEAD

import com.ems.entities.Party;
import org.openapitools.model.PartyDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
=======
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;
import org.springframework.stereotype.Service;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import java.util.List;

@Service
public interface PartyService {

<<<<<<< HEAD
    PartyDTO partyById(long id);
    PartyDTO saveParty(org.openapitools.model.PartyDTO partyDTO);
    List<PartyDTO> findAll();
=======
    PartyDataDTO partyById(long id);
    PartyDataDTO saveParty(PartyRegisterDTO partyDTO);
    List<PartyDataDTO> findAll();
    PartyDataDTO updateParty(Long partyId, PartyUpdateDTO partyUpdateDTO);
    void deleteParty(Long partyId);

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
