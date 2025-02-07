package com.ems.services;

import com.ems.dtos.ElectionDTO;
import com.ems.entities.Election;
import com.ems.mappers.ElectionMapper;
import com.ems.repositories.ElectionRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ElectionServiceImpl implements ElectionService{
    private final ElectionRepository electionRepository;
    private final ElectionMapper electionMapper;

    @Override
    public Election saveElection(ElectionDTO electionDTO) {
        var election=electionMapper.toElectionDTO(electionDTO);
        return electionRepository.save(election);
    }
}
