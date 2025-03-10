package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.VoterStatusDTO;
import org.openapitools.model.VoterStatusDataDTO;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VoterApiController implements VotersApi {

    private final VoterService voterService;

    @Override
    public ResponseEntity<VoterDTO> registerVoter(VoterRegisterDTO voterRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new VoterDTO(
                "Voter Registered Successfully",
                voterService.register(voterRegisterDTO)
        ));
    }

    @Override
    public ResponseEntity<PaginatedVoterDTO> searchVoters(
            Integer page, Integer size, String firstName, String lastName,
            LocalDate dateOfBirth, String dmvNumber, String ssnNumber,
            String city, List<String> sort) {

        String[] sortArray = (sort != null) ? sort.toArray(new String[0]) : new String[0];
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);

        Page<VoterDataDTO> voterDTOData = voterService.searchVoters(searchDTO, page, size, sortArray);

        PaginatedVoterDTO response = new PaginatedVoterDTO();
        response.setData(voterDTOData.getContent());
        response.setNumber(voterDTOData.getNumber());
        response.setSize(voterDTOData.getSize());
        response.setTotalElements(voterDTOData.getTotalElements());
        response.setTotalPages(voterDTOData.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<VoterDTO> votersVoterIdPatch(String voterId, VoterUpdateRequest voterUpdateRequest) {
        return ResponseEntity.ok(new VoterDTO(
                "Voter Updated Successfully",
                voterService.updateVoter(voterId, voterUpdateRequest)
                ));
    }

    @Override
    public ResponseEntity<VoterStatusDTO> getAllStatus() {
        List<VoterStatusDataDTO> statusList = voterService.getAllStatus();
        return ResponseEntity.ok(new VoterStatusDTO(
                "SuccessFully Fetched All Status",
                statusList
        ));
    }
}
