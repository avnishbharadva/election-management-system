package com.ems.services.impls;

import com.ems.dtos.PartyDTO;
import com.ems.entities.Party;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.services.PartyService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class PartyServiceImpl implements PartyService {
    private final PartyRepository partyRepository;
    private final GlobalMapper globalMapper;

    @Override
    public PartyDTO partyById(long id) {
        return null;
    }

    @Override
    public Party saveParty(PartyDTO partyDTO) {
        var party=globalMapper.toParty(partyDTO);
        return partyRepository.save(party);
    }
}
