package com.ems.services.impls;

import com.ems.dtos.ElectionDTO;
import com.ems.dtos.ElectionPageResponse;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Election;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.ElectionRepository;
import com.ems.services.ElectionService;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Data

@Service

public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final GlobalMapper globalMapper;
    private final CandidateMapper candidateMapper;

    @Override
    public Election saveElection(ElectionDTO electionDTO) {
        if (electionDTO.getTotalSeats() == null || electionDTO.getTotalSeats() < 1) {
            throw new IllegalArgumentException("Total seats cannot be null or less than 1");
        }
        Election election = globalMapper.toElectionDTO(electionDTO);
        return electionRepository.save(election);
    }

    @Override
    public Election updateElection(Long electionId, ElectionDTO electionDTO) {
        var existingElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new DataNotFoundException("No such election with id:" + electionId));
        Optional.ofNullable(electionDTO.getElectionName()).ifPresent(existingElection::setElectionName);
        Optional.ofNullable(electionDTO.getElectionDate()).ifPresent(existingElection::setElectionDate);
        Optional.ofNullable(electionDTO.getElectionType()).ifPresent(existingElection::setElectionType);
        Optional.ofNullable(electionDTO.getElectionState()).ifPresent(existingElection::setElectionState);

        if (electionDTO.getTotalSeats() != 0)
            existingElection.setTotalSeats(electionDTO.getTotalSeats());
        return electionRepository.save(existingElection);
    }

    @Override
    public ElectionPageResponse getElectionsSorted(String order, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, "desc".equalsIgnoreCase(order)
                ? Sort.by("electionDate").descending()
                : Sort.by("electionDate").ascending());
        Page<Election> electionsPage = electionRepository.findAll(pageable);
        List<ElectionSortDTO> electionDTOs = electionsPage.getContent()
                .stream()
                .map(candidateMapper::toElectionSortDTO)
                .toList();
        ElectionPageResponse response = new ElectionPageResponse();
        response.setElection(electionDTOs);
        response.setCurrentPage(electionsPage.getNumber());
        response.setPerPage(electionsPage.getSize());
        response.setTotalPages(electionsPage.getTotalPages());
        response.setTotalRecords(electionsPage.getTotalElements());
        return response;
    }

    @Override
    public void deleteElectionById(Long electionId) {
        if (!electionRepository.existsById(electionId))
            throw new DataNotFoundException("Election not found");
        electionRepository.deleteById(electionId);
    }

    @Override
    public List<ElectionDTO> getAllElection() {
        List<Election> elections = electionRepository.findAll();
        if (elections.isEmpty()) {
            throw new DataNotFoundException("No elections found");
        }
        return elections.stream().map(globalMapper::toElection).
                toList();
    }

}

