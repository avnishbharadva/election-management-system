package com.ems.services.impls;

import com.ems.entities.Election;
import com.ems.exceptions.CandidateAssociatedException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ElectionServiceImplTest {

    @Mock
    private ElectionRepository electionRepository;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private CandidateMapper candidateMapper;

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private ElectionServiceImpl electionService;

    private Election election;

    private static final Long ELECTION_ID=1L;

    private ElectionDTO electionDTO;

    private ElectionUpdateDTO electionUpdateDTO;

    @BeforeEach
    void setUp() {
        election = new Election();
        election.setElectionId(ELECTION_ID);
        election.setElectionName("Presidential Election");
        election.setElectionDate(LocalDate.of(2025,11,9));
        election.setElectionType("Local");
        election.setElectionState("New York");
        election.setTotalSeats(10);

        electionDTO = new ElectionDTO();
        electionDTO.setElectionName("Presidential Election");
        electionDTO.setElectionDate(LocalDate.of(2025,11,9));
        electionDTO.setElectionType("General");
        electionDTO.setElectionState("New York");
        electionDTO.setTotalSeats(10);

        electionUpdateDTO = new ElectionUpdateDTO();
        electionUpdateDTO.setElectionName("Mayor Election");
        electionUpdateDTO.setElectionDate(LocalDate.of(2025,11,9));
        electionUpdateDTO.setElectionType("State");
        electionUpdateDTO.setElectionState("LA");
        electionUpdateDTO.setTotalSeats(10);

    }

    @Test
    void getElectionById_Success() {
        when(electionRepository.findById(election.getElectionId())).thenReturn(Optional.of(election));
        ModelApiResponse response = electionService.getElectionById(election.getElectionId());
        assertTrue(response.getSuccess());
        assertEquals(Optional.of(election), response.getData());
    }

    @Test
    void getElectionById_NotFound() {
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> electionService.getElectionById(ELECTION_ID));
    }

    @Test
    void saveElection_Success() {
        when(globalMapper.toElectionDTO(electionDTO)).thenReturn(election);
        when(electionRepository.save(election)).thenReturn(election);
        ModelApiResponse response = electionService.saveElection(electionDTO);
        assertTrue(response.getSuccess());
        assertEquals(election, response.getData());
    }

    @Test
    void saveElection_InvalidTotalSeats() {
        electionDTO.setTotalSeats(0);
        assertThrows(IllegalArgumentException.class, () -> electionService.saveElection(electionDTO));
    }

    @Test
    void updateElection_Success() {
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));
        when(electionRepository.save(any(Election.class))).thenReturn(election);
        ModelApiResponse response = electionService.updateElection(ELECTION_ID, electionUpdateDTO);
        assertTrue(response.getSuccess());
        assertEquals(election, response.getData());
    }

    @Test
    void updateElection_NotFound() {
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> electionService.updateElection(ELECTION_ID, electionUpdateDTO));
    }

    @Test
    void getElectionsSorted_Ascending() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("electionDate").ascending());
        Page<Election> page = new PageImpl<>(List.of(election), pageable, 1);
        when(electionRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(candidateMapper.toElectionSortDTO(any(Election.class))).thenReturn(new ElectionSortDTO());
        ElectionPageResponse response = electionService.getElectionsSorted("asc", 0, 10);
        assertEquals(1, response.getTotalRecords());
    }

    @Test
    void getElectionsSorted_Descending() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("electionDate").descending());
        Page<Election> page = new PageImpl<>(List.of(election), pageable, 1);
        when(electionRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(candidateMapper.toElectionSortDTO(any(Election.class))).thenReturn(new ElectionSortDTO());
        ElectionPageResponse response = electionService.getElectionsSorted("desc", 0, 10);
        assertEquals(1, response.getTotalRecords());
    }

    @Test
    void deleteElectionById_Success() {
        when(electionRepository.existsById(ELECTION_ID)).thenReturn(true);
        when(candidateRepository.existsByElection_ElectionId(ELECTION_ID)).thenReturn(false);
        ModelApiResponse response = electionService.deleteElectionById(ELECTION_ID);
        assertTrue(response.getSuccess());
        verify(electionRepository, times(1)).deleteById(ELECTION_ID);
    }

    @Test
    void deleteElectionById_NotFound() {
        when(electionRepository.existsById(ELECTION_ID)).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> electionService.deleteElectionById(ELECTION_ID));
    }

    @Test
    void deleteElectionById_CandidateAssociated() {
        when(electionRepository.existsById(ELECTION_ID)).thenReturn(true);
        when(candidateRepository.existsByElection_ElectionId(ELECTION_ID)).thenReturn(true);
        assertThrows(CandidateAssociatedException.class, () -> electionService.deleteElectionById(ELECTION_ID));
    }

    @Test
    void getAllElection_Success() {
        when(electionRepository.findAll()).thenReturn(List.of(election));
        when(globalMapper.toElection(any(Election.class))).thenReturn(electionDTO);
        ModelApiResponse response = electionService.getAllElection();
        assertTrue(response.getSuccess());
        assertEquals(1, ((List<ElectionDTO>) response.getData()).size());
    }

    @Test
    void getAllElection_NotFound() {
        when(electionRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(DataNotFoundException.class, () -> electionService.getAllElection());
    }

}
