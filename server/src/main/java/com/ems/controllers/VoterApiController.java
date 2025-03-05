package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.services.impls.VoterServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VoterApiController implements VotersApi {

    private final VoterServiceImpl voterService;

    @Override
    public ResponseEntity<VoterDTO> registerVoter(VoterRegisterDTO voterRegisterDTO) {
        try {
            VoterDTO voterDTO = voterService.register(voterRegisterDTO);
            return new ResponseEntity<>(voterDTO, HttpStatus.CREATED);
        } catch (IllegalCredentials e) {
            log.error("Invalid credentials while registering voter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Error registering voter: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<PaginatedVoterDTO> searchVoters(Integer page, Integer size, String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, String city, List<String> sort) {
        try {
            String[] sortArray = (sort != null) ? sort.toArray(new String[0]) : new String[0];
            VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);

            Page<VoterDTO> voterPage = voterService.searchVoters(searchDTO, page, size, sortArray);

            if (voterPage.isEmpty()) {
                throw new DataNotFoundException("No voters found for the given criteria.");
            }

            PaginatedVoterDTO response = new PaginatedVoterDTO();
            response.setData(voterPage.getContent());
            response.setNumber(voterPage.getNumber());
            response.setSize(voterPage.getSize());
            response.setTotalElements(voterPage.getTotalElements());
            response.setTotalPages(voterPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (DataNotFoundException e) {
            log.warn("Search resulted in no voters found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error searching voters: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<VoterDTO> votersVoterIdPatch(String voterId, VoterDTO voterDTO) {
        try {
            return ResponseEntity.ok(voterService.updateVoter(voterId, voterDTO));
        } catch (DataNotFoundException e) {
            log.warn("Voter not found for ID {}: {}", voterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error updating voter with ID {}: {}", voterId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<VoterStatusDTO>> getAllStatus() {
        try {
            List<VoterStatusDTO> statuses = voterService.getAllStatus();
            if (statuses.isEmpty()) {
                throw new DataNotFoundException("No voter statuses found.");
            }
            return ResponseEntity.ok(statuses);
        } catch (DataNotFoundException e) {
            log.warn("No voter statuses found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error retrieving voter statuses: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
