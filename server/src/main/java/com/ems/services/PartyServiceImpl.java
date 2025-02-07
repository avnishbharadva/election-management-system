package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import com.ems.mappers.PartyMapper;
import com.ems.repositories.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartyServiceImpl {
    private final PartyRepository partyRepo;
    private final PartyMapper partyMapper;

    public PartyDTO partyById(long id){
        Optional<Party> partyOptional = partyRepo.findById(id);
        return partyOptional.map(partyMapper::toPartyDTO).orElse(null);
    }
}
