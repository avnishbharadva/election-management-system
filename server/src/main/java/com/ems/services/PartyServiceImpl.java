package com.ems.services;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.PartyMapper;
import com.ems.repositories.PartyRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class PartyServiceImpl implements PartyService{
    private final PartyRepository partyRepository;
    private final PartyMapper partyMapper;

    @Override
    public Party saveParty(PartyDTO partyDTO) {
        var party=partyMapper.toParty(partyDTO);
        return partyRepository.save(party);
    }
}
