package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;

import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepo;
    private final GlobalMapper globalMapper;

    public PartyDTO partyById(long id){
        Optional<Party> partyOptional = partyRepo.findById(id);
        return partyOptional.map(globalMapper::toPartyDTO).orElse(null);
    }
}
