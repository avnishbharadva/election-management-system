package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.services.AuditService;
import com.ems.services.HistoryService;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.openapitools.model.*;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VoterApiController implements VotersApi {

    private final VoterService voterService;
    private final AuditService auditService;
    private final HistoryService historyService;

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
    public ResponseEntity<VoterDTO> voterUpdate(String voterId, VoterUpdateRequest voterUpdateRequest) {
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

    @Override
    public ResponseEntity<CountyDTO> getAllCounties() {
        List<CountyDataDTO> countyList = voterService.getAllCounties();
        return ResponseEntity.ok(new CountyDTO(
                "Successfully Fetched All Counties",
                countyList
        ));
    }

    @Override
    public ResponseEntity<TownDTO> getAllTowns() {
        return ResponseEntity.ok(new TownDTO(
                "Successfully Fetched All Towns",
                voterService.getAllTowns()
        ));
    }

    @Override
    public ResponseEntity<AuditDTO> getAudit(String voterId) {
        log.info("voter audit called for voterId : {}", voterId);
        return new ResponseEntity<>(new AuditDTO(
                "Voter Audit Details Successfully fetched for : " + voterId,
                auditService.getAudit(voterId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoterDTO> transferVoter(String voterId, TransferAddress transferAddress) {
        log.info("Starting voter transfer for ID: {} | Request ID: {}", voterId, MDC.get("requestId"));
        VoterDataDTO transferredVoter = voterService.transferVoterAddress(voterId, transferAddress);
        log.info("Voter transfer successful for ID: {}", voterId);
        return ResponseEntity.ok(new VoterDTO("Voter Transferred Successfully", transferredVoter));
    }

    @Override
    public ResponseEntity<VoterDTO> changeVoter(String voterId, ChangeVoterAddress changeVoterAddress) {
        log.info("Starting address change for voter ID: {}", voterId);
        VoterDataDTO updatedVoter = voterService.changeVoterAddress(voterId, changeVoterAddress);
        log.info("Voter address changed successfully for ID: {}", voterId);
        return ResponseEntity.ok(new VoterDTO("Voter address changed Successfully", updatedVoter));
    }

    @Override
    public ResponseEntity<NameHistoryDTO> nameHistory(String voterId) {
        log.info("name history called for voterId : {}",voterId);
        return new ResponseEntity<>(new NameHistoryDTO(
                "Name history fetched for voter : " + voterId,
                historyService.getNameHistory(voterId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StatusHistoryDTO> statusHistory(String voterId) {
        log.info("status history called for voterId : {}", voterId);
        return new ResponseEntity<>(new StatusHistoryDTO(
                "Status history fetched for voter : " + voterId,
                historyService.getStatusHistory(voterId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AddressHistoryDTO> addressHistory(String voterId) {
        log.info("address history called for voterId : {}", voterId);
        return new ResponseEntity<>(new AddressHistoryDTO(
                "Address history fetched for voter : " + voterId,
                historyService.getAddressHistory(voterId)
        ), HttpStatus.OK);
    }
}
