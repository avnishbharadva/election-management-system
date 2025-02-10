package com.ems.services;

import com.ems.dtos.ElectionDTO;
import com.ems.entities.Candidate;
import com.ems.entities.Election;

public interface ElectionService {

    Election saveElection(ElectionDTO electionDTO);
    Election updateElection(Long electionId,ElectionDTO electionDTO);
}
