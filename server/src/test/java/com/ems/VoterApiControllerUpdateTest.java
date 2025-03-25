package com.ems;

import com.ems.controllers.VoterApiController;
import com.ems.services.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoterApiControllerUpdateTest {

    private static final Logger log = LoggerFactory.getLogger(VoterApiControllerUpdateTest.class);

    @Mock
    private VoterService voterService;

    @InjectMocks
    private VoterApiController voterApiController;

    private VoterUpdateRequest voterUpdateRequest;
    private VoterDataDTO updatedVoterDataDTO;
    private VoterDTO expectedVoterDTO;

    @BeforeEach
    void setUp() {
        voterUpdateRequest = new VoterUpdateRequest();
        voterUpdateRequest.setLastName("Patel");
        voterUpdateRequest.setDateOfBirth(LocalDate.of(1990, 5, 15));
        voterUpdateRequest.setDmvNumber("123456789");
        voterUpdateRequest.setEmail("kartik.patel@example.com");

        updatedVoterDataDTO = new VoterDataDTO();
        updatedVoterDataDTO.setFirstName("Kartik");
        updatedVoterDataDTO.setLastName("Patel");
        updatedVoterDataDTO.setDateOfBirth(LocalDate.of(1990, 5, 15));
        updatedVoterDataDTO.setDmvNumber("123456789");
        updatedVoterDataDTO.setEmail("kartik.patel@example.com");

        expectedVoterDTO = new VoterDTO();
        expectedVoterDTO.setMessage("Voter Updated Successfully");
    }

    @Test
    void updateVoter_Success() {
        log.info("Starting updateVoter_Success test");
        when(voterService.updateVoter(anyString(), any(VoterUpdateRequest.class))).thenReturn(updatedVoterDataDTO);
        log.info("voterService.updateVoter method called");
        var response = voterApiController.updateVoter("123456789", voterUpdateRequest);
        log.info("voterApiController.updateVoter method called");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        expectedVoterDTO.setData(updatedVoterDataDTO);
        assertEquals(expectedVoterDTO, response.getBody());
        log.info("Finished updateVoter_Success test");
    }
}