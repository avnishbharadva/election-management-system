package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.services.impls.VoterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.openapitools.api.VotersApi;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VoterApiController implements VotersApi {

    private final VoterServiceImpl voterService;

    @Override
    public ResponseEntity<VoterDTO> registerVoter(VoterRegisterDTO voterRegisterDTO) {
        VoterDTO voterDTO = voterService.register(voterRegisterDTO);
        return new ResponseEntity<>(voterDTO, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<PaginatedVoterDTO> searchVoters(Integer page, Integer size, String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, String city, List<String> sort) {
        String[] sortArray = (sort != null) ? sort.toArray(new String[0]) : new String[0];
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);

        Page<VoterDTO> voterPage = voterService.searchVoters(searchDTO,page,size,sortArray);

        // Map to response model
        PaginatedVoterDTO response = new PaginatedVoterDTO();
        response.setContent(voterPage.getContent());
        response.setNumber(voterPage.getNumber());
        response.setSize(voterPage.getSize());
        response.setTotalElements(voterPage.getTotalElements());
        response.setTotalPages(voterPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<VoterDTO> votersVoterIdPatch(String voterId, VoterDTO voterDTO) {
        return ResponseEntity.ok(voterService.updateVoter(voterId, voterDTO));
    }

    @Override
    public ResponseEntity<List<VoterStatusDTO>> getAllStatus() {
        return ResponseEntity.ok(voterService.getAllStatus());
    }
}
