package com.ems.services;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ModelApiResponse;

public interface ElectionService {

    ModelApiResponse saveElection(ElectionDTO electionDTO);

    ModelApiResponse updateElection(Long electionId,ElectionDTO electionDTO);
    ElectionPageResponse getElectionsSorted(String order, int page, int size);
    ModelApiResponse getElectionById(Long electionId);

    ModelApiResponse deleteElectionById(Long electionId);

    ModelApiResponse getAllElection();

}