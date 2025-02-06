package com.ems.services;

import com.ems.dtos.VoterDTO;
import com.ems.entities.Voter;
import com.ems.mapper.VoterMapper;
import com.ems.repositories.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepository;
    private final VoterMapper voterMapper;

    @Override
    public VoterDTO getVoterBySsn(String ssnNumber) {
        return voterRepository.findBySsnNumber(ssnNumber).map(voterMapper::toVoterDTO).orElseThrow();
    }
}