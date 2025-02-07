package com.ems.services.impls;

import com.ems.dtos.ElectionDTO;
import com.ems.entities.Election;
import com.ems.mappers.ElectionMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.ElectionRepository;
import com.ems.services.ElectionService;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final GlobalMapper globalMapper;

    @Override
    public Election saveElection(ElectionDTO electionDTO) {
        var election=globalMapper.toElectionDTO(electionDTO);
        return electionRepository.save(election);
    }
}
