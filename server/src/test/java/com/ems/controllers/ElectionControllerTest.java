package com.ems.controllers;

import com.ems.entities.Election;
import com.ems.services.ElectionService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ElectionUpdateDTO;
import org.openapitools.model.ModelApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class ElectionControllerTest {

    @Mock
    private ElectionService electionService;

    @InjectMocks
    private ElectionController electionController;

    private ElectionDTO electionDTO;
    private ElectionUpdateDTO electionUpdateDTO;
    private Election election;
    private ModelApiResponse apiResponse;
    private ElectionPageResponse pageResponse;
    private AutoCloseable closable;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);

        electionDTO = new ElectionDTO();
        electionDTO.setElectionId(1L);
        electionDTO.setElectionName("General Election");
        electionDTO.setElectionType("State");
        electionDTO.setElectionState("New York");
        electionDTO.setElectionDate(LocalDate.of(2025, 11, 9));
        electionDTO.setTotalSeats(200);

        electionUpdateDTO = new ElectionUpdateDTO();
        electionUpdateDTO.setElectionName("Updated Election");
        electionUpdateDTO.setElectionType("Federal");
        electionUpdateDTO.setElectionState("California");
        electionUpdateDTO.setElectionDate(LocalDate.of(2026, 5, 15));
        electionUpdateDTO.setTotalSeats(250);

        election = new Election();
        election.setElectionId(1L);
        election.setElectionName("General Election");
        election.setElectionType("State");
        election.setElectionState("New York");
        election.setElectionDate(LocalDate.of(2025, 11, 9));
        election.setTotalSeats(200);

        apiResponse = new ModelApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setTimestamp(OffsetDateTime.now());
        apiResponse.setData(election);

        pageResponse = new ElectionPageResponse();
        pageResponse.setCurrentPage(1);
        pageResponse.setPerPage(10);
        pageResponse.setTotalPages(1);
        pageResponse.setTotalRecords(1);
    }

    @Test
    void createElection_Success() {
        log.info("Executing test: createElection_Success");

        when(electionService.saveElection(electionDTO)).thenReturn(apiResponse);

        log.info("Calling electionController.createElection");
        ResponseEntity<ModelApiResponse> response = electionController.createElection(electionDTO);

        log.info("Response has received: {}", response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(election, response.getBody().getData());
    }

    @Test
    void createElection_ValidationFailsForEmptyDTO() {
        log.info("Executing test: createElection_ValidationFailsForEmptyDTO");

        ElectionDTO invalidElection = new ElectionDTO();
        invalidElection.setElectionDate(LocalDate.now().plusDays(2));

        jakarta.validation.Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(invalidElection);

        for (ConstraintViolation<ElectionDTO> violation : violations) {
            log.error("Validation failed - {}: {}", violation.getPropertyPath(), violation.getMessage());
        }

        assertFalse(violations.isEmpty(), "Validation should fail for an empty ElectionDTO");

        Set<String> expectedFields = Set.of("electionName", "electionType", "electionState", "totalSeats");
        Set<String> actualFields = violations.stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());

        assertEquals(expectedFields, actualFields, "Validation should fail for all required fields");
    }

    @Test
    void getAllElections_Success() {
        log.info("Executing test: getAllElections_Success");

        when(electionService.getAllElection()).thenReturn(apiResponse);

        log.info("Calling electionController.getAllElections method");
        ResponseEntity<ModelApiResponse> response = electionController.getAllElections();

        log.info("Response received successfully: {}", response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(election, response.getBody().getData());
    }

    @Test
    void getAllElections_NoElectionsFound() {
        log.info("Executing test: getAllElections_NoElectionsFound");

        ModelApiResponse emptyResponse = new ModelApiResponse();
        emptyResponse.setSuccess(true);
        emptyResponse.setData(null);
        when(electionService.getAllElection()).thenReturn(emptyResponse);

        log.info("Calling electionController.getAllElections");
        ResponseEntity<ModelApiResponse> response = electionController.getAllElections();

        log.info("Response received: {}", response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertNull(response.getBody().getData());
    }

    @Test
    void getElectionById_Success() {
        log.info("Executing test: getElectionById_Success");
        when(electionService.getElectionById(1L)).thenReturn(apiResponse);

        log.info("Calling electionController.getElectionById(1L)");
        ResponseEntity<ModelApiResponse> response = electionController.getElectionById(1L);

        log.info("Response has been received: {}", response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(election.getElectionId(), ((Election) response.getBody().getData()).getElectionId());
    }

    @Test
    void getElectionById_NotFound() {
        log.info("Starting test: getElectionById_NotFound");

        when(electionService.getElectionById(100L)).thenThrow(new RuntimeException("Election not found"));

        log.info("Calling getElectionById with ID: 100");
        Exception exception = assertThrows(RuntimeException.class, () -> electionController.getElectionById(100L));

        log.info("Exception has been caught: {}", exception.getMessage());

        verify(electionService, times(1)).getElectionById(100L);
        assertEquals("Election not found", exception.getMessage());

    }

    @Test
    void deleteElection_Success() {
        log.info("Starting test: deleteElection_Success");

        when(electionService.deleteElectionById(1L)).thenReturn(apiResponse);

        log.info("Calling deleteElection with ID: 1");
        ResponseEntity<ModelApiResponse> response = electionController.deleteElection(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());

    }

    @Test
    void deleteElection_NotFound() {
        log.info("Starting test: deleteElection_NotFound");

        when(electionService.deleteElectionById(100L)).thenThrow(new RuntimeException("Election not found"));

        log.info("Calling deleteElection with ID: 100");
        Exception exception = assertThrows(RuntimeException.class, () -> electionController.deleteElection(100L));

        verify(electionService, times(1)).deleteElectionById(100L);
        assertEquals("Election not found", exception.getMessage());

        log.info("Exception is being caught: {}", exception.getMessage());
    }

    @Test
    void updateElection_Success() {
        log.info("Starting test: updateElection_Success");

        when(electionService.updateElection(1L, electionUpdateDTO)).thenReturn(apiResponse);

        log.info("Calling updateElection with ID: 1");
        ResponseEntity<ModelApiResponse> response = electionController.updateElection(1L, electionUpdateDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(election, response.getBody().getData());

        log.info("Test updateElection_Success completed successfully.");
    }

    @Test
    void updateElection_InvalidId() {
        log.info("Starting test: updateElection_InvalidId");

        when(electionService.updateElection(100L, electionUpdateDTO)).thenThrow(new RuntimeException("Election not found"));

        log.info("Calling updateElection with ID: 100");
        Exception exception = assertThrows(RuntimeException.class, () -> electionController.updateElection(100L, electionUpdateDTO));

        verify(electionService, times(1)).updateElection(100L, electionUpdateDTO);
        assertEquals("Election not found", exception.getMessage());

        log.info("Exception caught : {}", exception.getMessage());
        log.info("Test updateElection_InvalidId completed successfully.");
    }

    @Test
    void getSortedElection_Success() {
        log.info("Starting test: getSortedElection_Success");

        when(electionService.getElectionsSorted("asc", 1, 10)).thenReturn(pageResponse);

        log.info("Calling getSortedElection with order: asc, page: 1, size: 10");
        ResponseEntity<ElectionPageResponse> response = electionController.getSortedElection("asc", 1, 10);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getCurrentPage());

        log.info("Test getSortedElection_Success completed successfully.");
    }

    @Test
    void getSortedElection_InvalidParams() {
        log.info("Starting test: getSortedElection_InvalidParams");

        when(electionService.getElectionsSorted("asc", -1, 0))
                .thenThrow(new IllegalArgumentException("Invalid pagination parameters"));

        log.info("Calling getSortedElection with invalid params: order=asc, page=-1, size=0");
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> electionController.getSortedElection("asc", -1, 0));

        verify(electionService, times(1)).getElectionsSorted("asc", -1, 0);
        assertEquals("Invalid pagination parameters", exception.getMessage());

        log.info("Exception caught: {}", exception.getMessage());
        log.info("Test getSortedElection_InvalidParams completed successfully.");
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

}
