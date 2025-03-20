package com.ems.services.impls;


import com.ems.entities.Election;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.ElectionRepository;
import com.ems.services.ElectionService;
import lombok.Data;
<<<<<<< HEAD
=======
import org.joda.time.field.OffsetDateTimeField;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ElectionSortDTO;
import org.openapitools.model.ModelApiResponse;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======
import org.w3c.dom.html.HTMLImageElement;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Data
@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final GlobalMapper globalMapper;
    private final CandidateMapper candidateMapper;


    @Override
    public ModelApiResponse getElectionById(Long electionId) {
        Optional<Election> electionDTO=electionRepository.findById(electionId);

        if(electionDTO.isEmpty())
            throw new DataNotFoundException("No election found");
<<<<<<< HEAD
        return new ModelApiResponse()
                .success(true)
                .message("Election retrieved successfully")
                .timestamp(OffsetDateTime.now())
                .data(electionDTO);
    }

=======
        return new ModelApiResponse("Election retrieved successfully", electionDTO , OffsetDateTime.now(),true);

    }




>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    @Override
    public ModelApiResponse saveElection(ElectionDTO electionDTO) {
        if (electionDTO.getTotalSeats() == null || electionDTO.getTotalSeats() < 1) {
            throw new IllegalArgumentException("Total seats cannot be null or less than 1");
        }

        Election election = globalMapper.toElectionDTO(electionDTO);
        Election savedElection = electionRepository.save(election);

<<<<<<< HEAD
        return new ModelApiResponse()
                .message("Election saved successfully")
                .data(savedElection)  // Set the saved election data
                .timestamp(OffsetDateTime.now())  // Use OffsetDateTime for timestamp
                .success(true);  // Set success flag
=======
        return new ModelApiResponse("Election saved successfully", savedElection , OffsetDateTime.now(),true);

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    }



    public ModelApiResponse updateElection(Long electionId, ElectionDTO electionDTO) {
        var existingElection = electionRepository.findById(electionId)
                .orElseThrow(() -> new DataNotFoundException("No such election with id: " + electionId));

        // Only update fields that are non-null in electionDTO
        Optional.ofNullable(electionDTO.getElectionName()).ifPresent(existingElection::setElectionName);
        Optional.ofNullable(electionDTO.getElectionDate()).ifPresent(existingElection::setElectionDate);
        Optional.ofNullable(electionDTO.getElectionType()).ifPresent(existingElection::setElectionType);
        Optional.ofNullable(electionDTO.getElectionState()).ifPresent(existingElection::setElectionState);

        // Update totalSeats only if it's provided (use Integer instead of int in DTO to check null)
        if (electionDTO.getTotalSeats() != null) {
            existingElection.setTotalSeats(electionDTO.getTotalSeats());
        }

        electionRepository.save(existingElection);
<<<<<<< HEAD

        return new ModelApiResponse().message("Election updated successfully")
                .data(existingElection) // Return the updated entity instead of DTO
                .timestamp(OffsetDateTime.now())
                .success(true);
=======
        return new ModelApiResponse("Election updated successfully", existingElection , OffsetDateTime.now(),true);

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
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
        response.setCurrentPage(electionsPage.getNumber());
        response.setPerPage(electionsPage.getSize());
        response.setElections(electionDTOs);
        response.setTotalPages(electionsPage.getTotalPages());
        response.setTotalRecords((int) electionsPage.getTotalElements());


        return response;
    }
<<<<<<< HEAD
 
=======

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

    @Override
    public ModelApiResponse deleteElectionById(Long electionId) {
        if (!electionRepository.existsById(electionId))
            throw new DataNotFoundException("Election not found");

        electionRepository.deleteById(electionId);
<<<<<<< HEAD
        return new ModelApiResponse().message("Election deleted successfully")
                .success(true)
                .timestamp(OffsetDateTime.now());
=======
        return new ModelApiResponse("Election deleted successfully",null , OffsetDateTime.now(),true);

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    }

    @Override
    public ModelApiResponse getAllElection() {
        List<Election> elections = electionRepository.findAll();
        if (elections.isEmpty()) {
            throw new DataNotFoundException("No elections found");
        }
        List<ElectionDTO> electionDTOs = elections.stream().map(globalMapper::toElection).toList();
<<<<<<< HEAD
        return new ModelApiResponse().message("Elections retrieved successfully")
                .data(electionDTOs)
                .timestamp(OffsetDateTime.now())
                .success(true);
    }
}
=======
        return new ModelApiResponse("Election retrieved successfully", electionDTOs , OffsetDateTime.now(),true);

    }
}

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
