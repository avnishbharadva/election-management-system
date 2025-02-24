package com.ems.services;

import com.ems.dtos.ElectionDTO;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Election;

import java.util.List;

public interface ElectionService {

    Election saveElection(ElectionDTO electionDTO);
    Election updateElection(Long electionId,ElectionDTO electionDTO);
    List<ElectionSortDTO> getElectionsSorted(String order);
    void deleteElectionById(Long electionId);

}
