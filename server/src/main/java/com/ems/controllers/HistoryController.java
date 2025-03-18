package com.ems.controllers;

import com.ems.services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.HistoryApi;
import org.openapitools.model.AddressHistoryDTO;
import org.openapitools.model.NameHistoryDTO;
import org.openapitools.model.StatusHistoryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class HistoryController implements HistoryApi {

    private final HistoryService historyService;

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
