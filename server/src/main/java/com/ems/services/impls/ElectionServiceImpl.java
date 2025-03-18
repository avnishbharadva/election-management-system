package com.ems.services.impls;
import com.ems.entities.Election;
import com.ems.exceptions.CandidateAssociatedException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.services.ElectionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.*;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final GlobalMapper globalMapper;
    private final CandidateMapper candidateMapper;
    private final CandidateRepository candidateRepository;


    @Override
    public ModelApiResponse getElectionById(Long electionId) {
        log.info("Fetching election details for ID: {}", electionId);
        Optional<Election> electionDTO = electionRepository.findById(electionId);

        if (electionDTO.isEmpty()) {
            log.warn("No election found with ID: {}", electionId);
            throw new DataNotFoundException("No election found with id:"+electionId);
        } else {
            log.debug("Election details found: {}", electionDTO.get().getElectionId());
        }

        return new ModelApiResponse()
                .success(true)
                .timestamp(OffsetDateTime.now())
                .data(electionDTO);
    }

    @Override
    public ModelApiResponse saveElection(ElectionDTO electionDTO) {
        log.info("Saving new election with details: {}", electionDTO);

        if (electionDTO.getTotalSeats() == null || electionDTO.getTotalSeats() < 1) {
            log.error("Invalid total seats value: {}", electionDTO.getTotalSeats());
            throw new IllegalArgumentException("Total seats cannot be null or less than 1");
        }

        Election election = globalMapper.toElectionDTO(electionDTO);
        Election savedElection = electionRepository.save(election);
        log.info("Election saved successfully with ID: {}", savedElection.getElectionId());

        return new ModelApiResponse()
                .data(savedElection)
                .timestamp(OffsetDateTime.now())
                .success(true);
    }

    @Override
    public ModelApiResponse updateElection(Long electionId, ElectionUpdateDTO electionDTO) {
        log.info("Updating election with ID: {}", electionId);
        var existingElection = electionRepository.findById(electionId)
                .orElseThrow(() -> {
                    log.warn("No election found with ID: {}", electionId);
                    return new DataNotFoundException("No such election with id: " + electionId);
                });

        log.debug("Existing election details: {}", existingElection);

        Optional.ofNullable(electionDTO.getElectionName()).ifPresent(existingElection::setElectionName);
        Optional.ofNullable(electionDTO.getElectionDate()).ifPresent(existingElection::setElectionDate);
        Optional.ofNullable(electionDTO.getElectionType()).ifPresent(existingElection::setElectionType);
        Optional.ofNullable(electionDTO.getElectionState()).ifPresent(existingElection::setElectionState);

        if (electionDTO.getTotalSeats() != null) {
            existingElection.setTotalSeats(electionDTO.getTotalSeats());
        }

        Election updatedElection = electionRepository.save(existingElection);
        log.info("Election with ID {} updated successfully", electionId);

        return new ModelApiResponse()
                .data(updatedElection)
                .timestamp(OffsetDateTime.now())
                .success(true);
    }

    @Override
    public ElectionPageResponse getElectionsSorted(String order, int page, int size) {
        log.info("Fetching elections sorted by electionDate in {} order, page: {}, size: {}", order, page, size);

        Pageable pageable = PageRequest.of(page, size, "desc".equalsIgnoreCase(order)
                ? Sort.by("electionDate").descending()
                : Sort.by("electionDate").ascending());

        Page<Election> electionsPage = electionRepository.findAll(pageable);

        if (electionsPage.isEmpty()) {
            log.warn("No elections found for the given page and sorting criteria");
        } else {
            log.debug("Fetched {} elections", electionsPage.getTotalElements());
        }

        List<ElectionSortDTO> electionDTOs = electionsPage.getContent()
                .stream()
                .map(candidateMapper::toElectionSortDTO)
                .toList();

        ElectionPageResponse response = new ElectionPageResponse();
        response.setCurrentPage(electionsPage.getNumber());
        response.setPerPage(electionsPage.getSize());
        response.setElections(electionDTOs);
        response.setTotalPages(electionsPage.getTotalPages());
        response.setTotalRecords((int) electionsPage.getTotalElements());

        return response;
    }

    @Override
    public ModelApiResponse deleteElectionById(Long electionId) {
        log.info("Deleting election with ID: {}", electionId);

        if (!electionRepository.existsById(electionId)) {
            log.warn("Attempted to delete non-existent election with ID: {}", electionId);
            throw new DataNotFoundException("Election not found");
        }

        boolean hasCandidate= candidateRepository.existsByElection_ElectionId(electionId);

        if(hasCandidate){
            log.warn("Cannot delete election id {} as candidates are associated to it ",electionId);
            throw new CandidateAssociatedException("Unable to delete because candidates are associated with this election");
        }

        electionRepository.deleteById(electionId);
        log.info("Election with ID {} deleted successfully", electionId);

        return new ModelApiResponse()
                .success(true)
                .timestamp(OffsetDateTime.now());
    }

    @Override
    public ModelApiResponse getAllElection() {
        log.info("Fetching all elections");

        List<Election> elections = electionRepository.findAll();
        if (elections.isEmpty()) {
            log.warn("No elections found in the database");
            throw new DataNotFoundException("No elections found");
        }

        log.debug("Fetched {} elections", elections.size());

        List<ElectionDTO> electionDTOs = elections.stream().map(globalMapper::toElection).toList();

        return new ModelApiResponse()
                .data(electionDTOs)
                .timestamp(OffsetDateTime.now())
                .success(true);
    }
}
