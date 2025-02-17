package com.ems.services.impls;

import com.ems.dtos.PartyDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final GlobalMapper globalMapper;

    @Override
    public PartyDTO partyById(long id) {
        var party = partyRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + id));
        return globalMapper.toPartyDTO(party);
    }

    @Override
    public PartyDTO saveParty(PartyDTO partyDTO) {
        var party = globalMapper.toParty(partyDTO);
        party = partyRepository.save(party);
        log.info("Party Successfully Saved : {}",party.getPartyName());
        return globalMapper.toPartyDTO(party);
    }

    @Override
    public List<PartyDTO> findAll() {
        return partyRepository.findAll().stream().map(globalMapper::toPartyDTO).toList();
    }
}
