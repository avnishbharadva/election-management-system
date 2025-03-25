package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
import com.ems.services.AuditService;
import com.ems.services.HistoryService;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.openapitools.model.VoterDTO;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterUpdateRequest;

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
    public ResponseEntity<org.openapitools.model.VoterDTO> registerVoter(org.openapitools.model.VoterRegisterDTO voterRegisterDTO) {
        log.info("Starting voter registration for: {}", voterRegisterDTO.getFirstName());
        org.openapitools.model.VoterDataDTO voterData = voterService.register(voterRegisterDTO);
        log.info("Voter registration successful for: {}", voterRegisterDTO.getFirstName());
        return ResponseEntity.status(HttpStatus.CREATED).body(new VoterDTO("Voter Registered Successfully", voterData));
    }

    @Override
    public ResponseEntity<org.openapitools.model.PaginatedVoterDTO> searchVoters(
            Integer page, Integer size, String firstName, String lastName,
            LocalDate dateOfBirth, String dmvNumber, String ssnNumber,
            String city, List<String> sort) {
        log.info("Starting voter search with criteria: firstName={}, lastName={}, city={}", firstName, lastName, city);

        String[] sortArray = (sort != null) ? sort.toArray(new String[0]) : new String[0];
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);
        Page<org.openapitools.model.VoterDataDTO> voterDTOData;
        voterDTOData = voterService.searchVoters(searchDTO, page, size, sortArray);log.info("Voter search completed. Total results found: {}", voterDTOData.getTotalElements());
        var response = new org.openapitools.model.PaginatedVoterDTO(voterDTOData.getContent(), voterDTOData.getTotalElements(), voterDTOData.getTotalPages(), voterDTOData.getSize(), voterDTOData.getNumber());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<VoterDTO> updateVoter(String voterId, VoterUpdateRequest voterUpdateRequest) {
        log.info("Starting voter update for ID: {}", voterId);
        VoterDataDTO updatedVoter = voterService.updateVoter(voterId, voterUpdateRequest);
        log.info("Voter update successful for ID: {}", voterId);
        return ResponseEntity.ok(new VoterDTO("Voter Updated Successfully", updatedVoter));
    }



    @Override
    public ResponseEntity<org.openapitools.model.VoterStatusDTO> getAllStatus() {
        log.info("Fetching all voter statuses");
        List<org.openapitools.model.VoterStatusDataDTO> statusList = voterService.getAllStatus();
        log.info("Successfully fetched {} voter statuses", statusList.size());
        return ResponseEntity.ok(new org.openapitools.model.VoterStatusDTO("Successfully Fetched All Status", statusList));
    }

    @Override
    public ResponseEntity<org.openapitools.model.AuditDTO> getAudit(String voterId) {
        log.info("voter audit called for voterId : {}", voterId);
        return new ResponseEntity<>(new org.openapitools.model.AuditDTO(
                "Voter Audit Details Successfully fetched for : " + voterId,
                auditService.getAudit(voterId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VoterDTO> transferVoter(String voterId, org.openapitools.model.TransferAddress transferAddress) {
        log.info("Starting voter transfer for ID: {} | Request ID: {}", voterId, MDC.get("requestId"));
        org.openapitools.model.VoterDataDTO transferredVoter = voterService.transferVoterAddress(voterId, transferAddress);
        log.info("Voter transfer successful for ID: {}", voterId);
        return ResponseEntity.ok(new VoterDTO("Voter Transferred Successfully", transferredVoter));
    }

    @Override
    public ResponseEntity<VoterDTO> changeVoter(String voterId, org.openapitools.model.ChangeVoterAddress changeVoterAddress) {
        log.info("Starting address change for voter ID: {} | Address type: {}", voterId, changeVoterAddress.getAddressType());
        org.openapitools.model.VoterDataDTO updatedVoter = voterService.changeVoterAddress(voterId, changeVoterAddress);
        log.info("Voter address updated successfully for ID: {}", voterId);
        return ResponseEntity.ok(new VoterDTO("Voter Updated Successfully", updatedVoter));
    }


//    Override
//    public ResponseEntity<NameHistoryDTO> nameHistory(String voterId) {
//        log.info("name history called for voterId : {}",voterId);
//        return new ResponseEntity<>(new NameHistoryDTO(
//                "Name history fetched for voter : " + voterId,
//                historyService.getNameHistory(voterId)
//        ), HttpStatus.OK);
//    }

    @Override
    public ResponseEntity<org.openapitools.model.StatusHistoryDTO> statusHistory(String voterId) {
        log.info("status history called for voterId : {}", voterId);
        return new ResponseEntity<>(new org.openapitools.model.StatusHistoryDTO(
                "Status history fetched for voter : " + voterId,
                historyService.getStatusHistory(voterId)
        ), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<org.openapitools.model.AddressHistoryDTO> addressHistory(String voterId) {
        log.info("address history called for voterId : {}", voterId);
        return new ResponseEntity<>(new org.openapitools.model.AddressHistoryDTO(
                "Address history fetched for voter : " + voterId,
                historyService.getAddressHistory(voterId)
        ), HttpStatus.OK);
    }
}
