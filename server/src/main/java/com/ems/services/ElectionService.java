package com.ems.services;

import com.ems.dtos.ElectionDTO;

import com.ems.dtos.ElectionPageResponse;
import com.ems.dtos.ElectionSortDTO;

import com.ems.entities.Election;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ElectionService {

    Election saveElection(ElectionDTO electionDTO);

    Election updateElection(Long electionId,ElectionDTO electionDTO);
    ElectionPageResponse getElectionsSorted(String order, int page, int size);


    void deleteElectionById(Long electionId);

    List<ElectionDTO> getAllElection();

}

