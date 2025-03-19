package com.ems.controllers;

import com.ems.services.ElectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ElectionDTO;
import org.openapitools.model.ElectionPageResponse;
import org.openapitools.model.ElectionUpdateDTO;
import org.openapitools.model.ModelApiResponse;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionControllerTest {

    @Mock
    private ElectionService electionService;

    @InjectMocks
    private ElectionController electionController;

    private ElectionDTO electionDTO;
    private ElectionUpdateDTO electionUpdateDTO;
    private ModelApiResponse apiResponse;
    private ElectionPageResponse pageResponse;

    @BeforeEach
    void setUp() {
        electionDTO = new ElectionDTO();
        electionDTO.setElectionId(1L);
        electionDTO.setElectionName("General Election");

        electionUpdateDTO = new ElectionUpdateDTO();
        electionUpdateDTO.setElectionName("Updated Election");

        apiResponse = new ModelApiResponse();
        apiResponse.setSuccess(true);
        apiResponse.setTimestamp(OffsetDateTime.now());
        apiResponse.setData("Election Created Successfully");

        pageResponse = new ElectionPageResponse();
    }

    @Test
    void createElection_Success() {
        when(electionService.saveElection(electionDTO)).thenReturn(apiResponse);
        ResponseEntity<ModelApiResponse> response = electionController.createElection(electionDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals("Election Created Successfully", response.getBody().getData());
    }

    @Test
    void createElection_Failure() {
        when(electionService.saveElection(electionDTO)).thenThrow(new RuntimeException("Election creation failed"));

        Exception exception = assertThrows(RuntimeException.class, () -> electionController.createElection(electionDTO));
        assertEquals("Election creation failed", exception.getMessage());
    }

    @Test
    void getAllElections_Success() {
        when(electionService.getAllElection()).thenReturn(apiResponse);
        ResponseEntity<ModelApiResponse> response = electionController.getAllElections();

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    void getAllElections_EmptyList() {
        ModelApiResponse emptyResponse = new ModelApiResponse();
        emptyResponse.setSuccess(true);
        emptyResponse.setData(Collections.emptyList());

        when(electionService.getAllElection()).thenReturn(emptyResponse);

        ResponseEntity<ModelApiResponse> response = electionController.getAllElections();
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
        assertEquals(Collections.emptyList(), response.getBody().getData());
    }

    @Test
    void deleteElection_Success() {
        when(electionService.deleteElectionById(1L)).thenReturn(apiResponse);
        ResponseEntity<ModelApiResponse> response = electionController.deleteElection(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    void deleteElection_NotFound() {
        when(electionService.deleteElectionById(99L)).thenThrow(new NoSuchElementException("Election not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> electionController.deleteElection(99L));
        assertEquals("Election not found", exception.getMessage());
    }

    @Test
    void getElectionById_Success() {
        when(electionService.getElectionById(1L)).thenReturn(apiResponse);
        ResponseEntity<ModelApiResponse> response = electionController.getElectionById(1L);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    void getElectionById_NotFound() {
        when(electionService.getElectionById(99L)).thenThrow(new NoSuchElementException("Election not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> electionController.getElectionById(99L));
        assertEquals("Election not found", exception.getMessage());
    }

    @Test
    void getSortedElection_Success() {
        when(electionService.getElectionsSorted("asc", 0, 10)).thenReturn(pageResponse);
        ResponseEntity<ElectionPageResponse> response = electionController.getSortedElection("asc", 0, 10);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
    }

    @Test
    void getSortedElection_InvalidSorting() {
        when(electionService.getElectionsSorted("invalid", 0, 10))
                .thenThrow(new IllegalArgumentException("Invalid sorting order"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> electionController.getSortedElection("invalid", 0, 10));
        assertEquals("Invalid sorting order", exception.getMessage());
    }

    @Test
    void updateElection_Success() {
        when(electionService.updateElection(1L, electionUpdateDTO)).thenReturn(apiResponse);
        ResponseEntity<ModelApiResponse> response = electionController.updateElection(1L, electionUpdateDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getSuccess());
    }

    @Test
    void updateElection_NotFound() {
        when(electionService.updateElection(99L, electionUpdateDTO)).thenThrow(new NoSuchElementException("Election not found"));

        Exception exception = assertThrows(NoSuchElementException.class, () -> electionController.updateElection(99L, electionUpdateDTO));
        assertEquals("Election not found", exception.getMessage());
    }
}
