package com.ems.controllers;

import com.ems.dtos.VoterSearchDTO;
<<<<<<< HEAD
import com.ems.services.impls.VoterServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDTO;
=======
import com.ems.services.AuditService;
import com.ems.services.HistoryService;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.VotersApi;
import org.slf4j.MDC;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD

=======
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterStatusDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.AuditDTO;
import org.openapitools.model.TransferAddress;
import org.openapitools.model.ChangeVoterAddress;
import org.openapitools.model.NameHistoryDTO;
import org.openapitools.model.StatusHistoryDTO;
import org.openapitools.model.AddressHistoryDTO;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VoterApiController implements VotersApi {

<<<<<<< HEAD
    private final VoterServiceImpl voterService;

    @Override
    public ResponseEntity<VoterDTO> registerVoter(VoterRegisterDTO voterRegisterDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(voterService.register(voterRegisterDTO));
    }


=======
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

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    @Override
    public ResponseEntity<PaginatedVoterDTO> searchVoters(
            Integer page, Integer size, String firstName, String lastName,
            LocalDate dateOfBirth, String dmvNumber, String ssnNumber,
            String city, List<String> sort) {

        String[] sortArray = (sort != null) ? sort.toArray(new String[0]) : new String[0];
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber, city);

<<<<<<< HEAD
        Page<VoterDTO> voterPage = voterService.searchVoters(searchDTO, page, size, sortArray);

        PaginatedVoterDTO response = new PaginatedVoterDTO();
        response.setData(voterPage.getContent());
        response.setNumber(voterPage.getNumber());
        response.setSize(voterPage.getSize());
        response.setTotalElements(voterPage.getTotalElements());
        response.setTotalPages(voterPage.getTotalPages());
=======
        Page<VoterDataDTO> voterDTOData = voterService.searchVoters(searchDTO, page, size, sortArray);

        PaginatedVoterDTO response = new PaginatedVoterDTO();
        response.setData(voterDTOData.getContent());
        response.setNumber(voterDTOData.getNumber());
        response.setSize(voterDTOData.getSize());
        response.setTotalElements(voterDTOData.getTotalElements());
        response.setTotalPages(voterDTOData.getTotalPages());
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

        return ResponseEntity.ok(response);
    }

<<<<<<< HEAD
=======
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
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

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

<<<<<<< HEAD
=======
    @Override
    public ResponseEntity<VoterDTO> changeVoter(String voterId, ChangeVoterAddress changeVoterAddress) {
        log.info("Starting address change for voter ID: {} | Address type: {}", voterId, changeVoterAddress.getAddressType());
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
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
