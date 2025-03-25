package com.ems;

import com.ems.controllers.VoterApiController;
import com.ems.dtos.VoterSearchDTO;
import com.ems.services.VoterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.PaginatedVoterDTO;
import org.openapitools.model.VoterDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoterApiControllerSearchTest {

    private static final Logger log = LoggerFactory.getLogger(VoterApiControllerSearchTest.class);

    @Mock
    private VoterService voterService;

    @InjectMocks
    private VoterApiController voterApiController;

    private Page<VoterDataDTO> voterDataPage;
    private String[] sortList;
    private PaginatedVoterDTO expected;

    @BeforeEach
    void setUp() {
        sortList = new String[]{"firstName"};
        voterDataPage = mock(Page.class);
        List<VoterDataDTO> content = List.of(new VoterDataDTO(), new VoterDataDTO());
        when(voterDataPage.getContent()).thenReturn(content);
        when(voterDataPage.getTotalElements()).thenReturn(10L);
        when(voterDataPage.getTotalPages()).thenReturn(1);
        when(voterDataPage.getSize()).thenReturn(10);
        when(voterDataPage.getNumber()).thenReturn(0);

        expected = new PaginatedVoterDTO();
        expected.setTotalElements(10L);
        expected.setData(content);
        expected.setTotalPages(1);
        expected.setSize(10);
        expected.setNumber(0);
    }

    @Test
    void searchVoters_Success() {
        log.info("Starting searchVoters_Success test");
        when(voterService.searchVoters(any(VoterSearchDTO.class), anyInt(), anyInt(), any(String[].class)))
                .thenReturn(voterDataPage);
        log.info("voterService searchVoters method called");

        ResponseEntity<PaginatedVoterDTO> response = voterApiController.searchVoters(
                0, 10, "John", "Doe", LocalDate.of(1990, 1, 1),
                "123456789", "987654321", "Las Vegas", List.of(sortList));
        log.info("voterApiController searchVoters method called");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expected, response.getBody());
        verify(voterService, times(1)).searchVoters(any(VoterSearchDTO.class), eq(0), eq(10), eq(sortList));
        log.info("voterService searchVoters method verified");
        log.info("Finished searchVoters_Success test");
    }

    @Test
    void searchVoters_NoResults() {
        log.info("Starting searchVoters_NoResults test");
        when(voterDataPage.getContent()).thenReturn(List.of());
        when(voterDataPage.getTotalElements()).thenReturn(0L);
        when(voterDataPage.getTotalPages()).thenReturn(0);
        when(voterDataPage.getSize()).thenReturn(10);
        when(voterDataPage.getNumber()).thenReturn(0);

        when(voterService.searchVoters(any(VoterSearchDTO.class), anyInt(), anyInt(), any(String[].class)))
                .thenReturn(voterDataPage);
        log.info("voterService searchVoters method called");

        ResponseEntity<PaginatedVoterDTO> response = voterApiController.searchVoters(
                0, 10, "Nonexistent", null, null, null, null, null, List.of(sortList));
        log.info("voterApiController searchVoters method called");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getData().size());
        assertEquals(0L, response.getBody().getTotalElements());
        verify(voterService, times(1)).searchVoters(any(VoterSearchDTO.class), eq(0), eq(10), eq(sortList));
        log.info("voterService searchVoters method verified");
        log.info("Finished searchVoters_NoResults test");
    }

    @Test
    void searchVoters_NullSortList() {
        log.info("Starting searchVoters_NullSortList test");
        when(voterService.searchVoters(any(VoterSearchDTO.class), anyInt(), anyInt(), any(String[].class)))
                .thenReturn(voterDataPage);
        log.info("voterService.searchVoters method called");

        ResponseEntity<PaginatedVoterDTO> response = voterApiController.searchVoters(
                0, 10, "John", "Doe", LocalDate.of(1990, 1, 1),
                "123456789", "987654321", "Las Vegas", null);
        log.info("voterApiController.searchVoters method called");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expected, response.getBody());
        verify(voterService, times(1)).searchVoters(any(VoterSearchDTO.class), eq(0), eq(10), eq(new String[0]));
        log.info("voterService.searchVoters method verified");
        log.info("Finished searchVoters_NullSortList test");
    }
}