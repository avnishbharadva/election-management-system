package com.ems.services.impls;

import com.ems.entities.Election;
import com.ems.exceptions.CandidateAssociatedException;
import com.ems.exceptions.CustomValidationException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.*;
import org.springframework.data.domain.*;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
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

    @Mock
    private ValidationErrorResponse validationErrorResponse;

    private Election election;

    private static final Long ELECTION_ID=1L;



    private ElectionDTO electionDTO;


    private ElectionUpdateDTO electionUpdateDTO;
    private ElectionSortDTO electionSortDTO;
    private Election election1;
    private Election election2;
    private Election election3;

    @BeforeEach
    void setUp() {
        log.info("Setting up test data...");

        election1 = new Election();
        election1.setElectionId(1L);
        election1.setElectionName("Election 1");
        election1.setTotalSeats(10);
        election1.setElectionType("General");
        election1.setElectionDate(LocalDate.of(2025, 11, 9));
        election1.setElectionState("New York");
        log.debug("Election 1: {}", election1);

        election2 = new Election();
        election2.setElectionId(2L);
        election2.setElectionName("Election 2");
        election2.setTotalSeats(10);
        election2.setElectionType("General");
        election2.setElectionDate(LocalDate.of(2025, 11, 3));
        election2.setElectionState("California");
        log.debug("Election 2: {}", election2);

        election3 = new Election();
        election3.setElectionId(3L);
        election3.setElectionName("Election 3");
        election3.setTotalSeats(10);
        election3.setElectionType("General");
        election3.setElectionDate(LocalDate.of(2025, 11, 10));
        election3.setElectionState("Texas");
        log.debug("Election 3: {}", election3);

        election = new Election();
        election.setElectionId(ELECTION_ID);
        election.setElectionName("Presidential Election");
        election.setElectionDate(LocalDate.of(2025,11,9));
        election.setElectionType("Local");
        election.setElectionState("New York");
        election.setTotalSeats(10);

        electionDTO = new ElectionDTO();
        electionDTO.setElectionId(ELECTION_ID);
        electionDTO.setElectionName("Presidential Election");
        electionDTO.setElectionDate(LocalDate.of(2025,11,9));
        electionDTO.setElectionType("General");
        electionDTO.setElectionState("New York");
        electionDTO.setTotalSeats(10);

        electionUpdateDTO = new ElectionUpdateDTO();
        electionUpdateDTO.setElectionId(ELECTION_ID);
        electionUpdateDTO.setElectionName("Mayor Election");
        electionUpdateDTO.setElectionDate(LocalDate.of(2025,11,9));
        electionUpdateDTO.setElectionType("State");
        electionUpdateDTO.setElectionState("New York");
        electionUpdateDTO.setTotalSeats(10);

        electionSortDTO = new ElectionSortDTO();
        electionSortDTO.setElectionId(1L);
        electionSortDTO.setElectionName("Presidential Election");
        electionSortDTO.setElectionDate(LocalDate.of(2025, 11, 9));
        electionSortDTO.setElectionType("General");
        electionSortDTO.setElectionState("New York");
        electionSortDTO.setTotalSeats(10);

    }

    @Test
    void getElectionById_Success() {
        when(electionRepository.findById(election.getElectionId())).thenReturn(Optional.of(election));
        log.info("Calling getById with ID: {}",election.getElectionId());
        ModelApiResponse response = electionService.getElectionById(election.getElectionId());
        log.info("Response Success: {}", response.getSuccess());
        log.info("Response Data: {}", response.getData());
        assertTrue(response.getSuccess());
        assertEquals(Optional.of(election), response.getData());
        verify(electionRepository, times(1)).findById(election.getElectionId());
        log.info("Verified that findById() was called exactly once.");
    }

    @Test
    void getElectionById_NotFound() {
        log.info("Starting test: getElectionById_NotFound");
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.empty());

        log.debug("Attempting to fetch election with ID: {}", ELECTION_ID);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> electionService.getElectionById(ELECTION_ID));

        log.error("Expected exception caught:: {}", exception.getMessage());

        verify(electionRepository, times(1)).findById(ELECTION_ID);

        assertEquals("No election found with id:" + ELECTION_ID, exception.getMessage());
    }

    @Test
    void saveElection_Fails_WhenElectionDateInPast() {
        log.info("Starting test: saveElection_Fails_WhenElectionDateInPast");
        electionDTO.setElectionDate(LocalDate.now().minusDays(1));

        log.debug("Attempting to save election with past date: {}", electionDTO.getElectionDate());

        CustomValidationException exception = assertThrows(CustomValidationException.class,
                () -> electionService.saveElection(electionDTO));

        log.error("Exception caught: {}", exception.getMessage());

        verify(electionRepository, never()).save(any(Election.class));

        assertEquals("Election date can't be past", exception.getMessage());
    }

    @Test
    void saveElection_Success_WhenElectionDateIsNotInPast(){
        log.info("Starting test:saveElection_Success_WhenElectionDateIsNotInPast ");
        electionDTO.setElectionDate(LocalDate.now().plusDays(3));

        log.debug("Attempting to save election with future date: {}",electionDTO.getElectionDate());

        when(globalMapper.toElectionDTO(electionDTO)).thenReturn(election);
        when(electionRepository.save(election)).thenReturn(election);
        ModelApiResponse response= electionService.saveElection(electionDTO);

        verify(electionRepository, times(1)).save(any(Election.class));
        assertEquals(election,response.getData());
    }

    @Test
    void saveElection_InvalidTotalSeats() {
        log.info("Starting test: saveElection_InvalidTotalSeats");
        electionDTO.setTotalSeats(0);
        log.debug("Attempting to save election with invalid total seats: {}", electionDTO.getTotalSeats());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> electionService.saveElection(electionDTO));


        log.error("Exception has been caught: {}", exception.getMessage());
        verify(electionRepository, never()).save(any(Election.class));

        assertEquals("Total seats cannot be null or less than 1", exception.getMessage());
    }

    @Test
    void saveElection_Success() {
        log.info("Starting test: saveElection_Success");

        when(globalMapper.toElectionDTO(electionDTO)).thenReturn(election);
        when(electionRepository.save(election)).thenReturn(election);

        log.debug("Attempting to save election with details: {}", electionDTO);

        ModelApiResponse response = electionService.saveElection(electionDTO);

        log.info("Election saved successfully with ID: {}", election.getElectionId());

        verify(electionRepository, times(1)).save(any(Election.class));
        assertTrue(response.getSuccess());
        assertEquals(election, response.getData());
    }

    @Test
    void updateElection_Success() {
        log.info("Starting test: updateElection_Success");

        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));
        log.debug("Election found for update: {}", election);

        when(electionRepository.save(any(Election.class))).thenReturn(election);
        log.debug("Attempting to update election with details: {}", electionUpdateDTO);

        ModelApiResponse response = electionService.updateElection(ELECTION_ID, electionUpdateDTO);

        verify(electionRepository, times(1)).findById(ELECTION_ID);
        verify(electionRepository, times(1)).save(any(Election.class));

        log.info("Election updated successfully with ID: {}", election.getElectionId());
        log.info("Updated election details: {}", response.getData());

        assertTrue(response.getSuccess());
        assertEquals(election, response.getData());
    }

    @Test
    void updateElection_Fails_WhenElectionCompleted() {
        log.info("Starting test: updateElection_Fails_WhenElectionCompleted");
        election.setElectionDate(LocalDate.now().minusDays(1));
        log.debug("Mocking election with ID: {} and past election date: {}",
                election.getElectionId(), election.getElectionDate());
        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));

        log.info("Calling updateElection with a past election date, expecting CustomValidationException");

        CustomValidationException exception = assertThrows(CustomValidationException.class,
                () -> electionService.updateElection(ELECTION_ID, electionUpdateDTO));

        log.error("Expected exception caught: {}", exception.getMessage());

        verify(electionRepository, times(1)).findById(ELECTION_ID);
        verify(electionRepository,never()).save(any(Election.class));


        assertEquals("Election is completed and cannot be updated.", exception.getMessage());

      }

    @Test
    void updateElection_Fails_WhenSeatsAreInvalid(){
        log.info("Starting test: updateElection_Fails_WhenSeatsAreInvalid");

        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.of(election));

        electionUpdateDTO.setTotalSeats(0);
        log.debug("Attempting to update election with invalid total seats: {}", electionUpdateDTO.getTotalSeats());


        CustomValidationException exception = assertThrows(CustomValidationException.class,
                () -> electionService.updateElection(ELECTION_ID,electionUpdateDTO));

        log.error("Exception is being caught: {}", exception.getMessage());
        verify(electionRepository, times(1)).findById(ELECTION_ID);
        verify(electionRepository, never()).save(any(Election.class));


        assertEquals("Total seats cannot be null or less than 1", exception.getMessage());

    }

    @Test
    void updateElection_NotFound() {
        log.info("Starting test: updateElection_NotFound for ID: {}", ELECTION_ID);

        when(electionRepository.findById(ELECTION_ID)).thenReturn(Optional.empty());
        log.debug("Election not found in repository for ID: {}", ELECTION_ID);

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> electionService.updateElection(ELECTION_ID, electionUpdateDTO));

        log.error("Exception caught: {}", exception.getMessage());
        verify(electionRepository,times(1)).findById(ELECTION_ID);
        verify(electionRepository,never()).save(election);

        assertEquals("No such election with id: " + ELECTION_ID, exception.getMessage());
    }

    @Test
    void getElectionsSorted_Ascending() {
        log.info("Starting test: getElectionsSorted_Ascending");

        List<Election> elections = List.of(election1, election2, election3)
                .stream()
                .sorted(Comparator.comparing(Election::getElectionDate))
                .toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("electionDate").ascending());
        Page<Election> page = new PageImpl<>(elections, pageable, elections.size());

        when(electionRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(candidateMapper.toElectionSortDTO(any(Election.class)))
                .thenAnswer(invocation -> {
                    Election election = invocation.getArgument(0);
                    log.debug("Mapping election: {}", election);

                    ElectionSortDTO dto = new ElectionSortDTO();
                    dto.setElectionId(election.getElectionId());
                    dto.setElectionName(election.getElectionName());
                    dto.setElectionDate(election.getElectionDate());
                    dto.setElectionState(election.getElectionState());
                    dto.setElectionType(election.getElectionType());
                    dto.setTotalSeats(election.getTotalSeats());
                    return dto;
                });

        log.debug("Mocked repository and mapper, calling service method...");

        ElectionPageResponse response = electionService.getElectionsSorted("asc", 0, 10);

        verify(electionRepository, times(1)).findAll(any(Pageable.class));
        log.info("Received response: {}", response);

        for (ElectionSortDTO election : response.getElections()) {
            log.info("Election in response: {}", election);
        }

        assertNotNull(response, "Response should not be null");
        assertEquals(3, response.getTotalRecords(), "Total records should be 3");
        assertEquals(3, response.getElections().size(), "Election list should have 3 elements");

        assertEquals("Election 2", response.getElections().get(0).getElectionName()); // 2025-11-03
        assertEquals("Election 1", response.getElections().get(1).getElectionName()); // 2025-11-09
        assertEquals("Election 3", response.getElections().get(2).getElectionName()); // 2025-11-10
    }

    @Test
    void getElectionsSorted_Descending() {
        log.info("Starting test: getElectionsSorted_Descending");

        List<Election> elections = List.of(election1, election2, election3)
                .stream()
                .sorted(Comparator.comparing(Election::getElectionDate).reversed()) // Sorting in descending order
                .toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("electionDate").descending());
        Page<Election> page = new PageImpl<>(elections, pageable, elections.size());

        when(electionRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(candidateMapper.toElectionSortDTO(any(Election.class)))
                .thenAnswer(invocation -> {
                    Election election = invocation.getArgument(0);
                    ElectionSortDTO dto = new ElectionSortDTO();
                    dto.setElectionId(election.getElectionId());
                    dto.setElectionName(election.getElectionName());
                    dto.setElectionDate(election.getElectionDate());
                    dto.setTotalSeats(election.getTotalSeats());
                    dto.setElectionType(election.getElectionType());
                    dto.setElectionState(election.getElectionState());
                    return dto;
                });

        log.debug("Mocked repository and mapper, calling service method...");

        ElectionPageResponse response = electionService.getElectionsSorted("desc", 0, 10);

        verify(electionRepository, times(1)).findAll(any(Pageable.class));
        log.info("Received response: {}", response);

        assertNotNull(response, "Response should not be null");
        assertEquals(3, response.getTotalRecords(), "Total records should be 3");
        assertEquals(3, response.getElections().size(), "Election list should have 3 elements");

        assertEquals("Election 3", response.getElections().get(0).getElectionName());
        assertEquals("Election 1", response.getElections().get(1).getElectionName());
        assertEquals("Election 2", response.getElections().get(2).getElectionName());
    }

    @Test
    void getElectionsSorted_InvalidOrder_DefaultsToAscending() {
        log.info("Starting test: getElectionsSorted_InvalidOrder_DefaultsToAscending");

        List<Election> elections = List.of(election1, election2, election3)
                .stream()
                .sorted(Comparator.comparing(Election::getElectionDate)) // Ascending order
                .toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("electionDate").ascending());
        Page<Election> page = new PageImpl<>(elections, pageable, elections.size());

        when(electionRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(candidateMapper.toElectionSortDTO(any(Election.class)))
                .thenAnswer(invocation -> {
                    Election election = invocation.getArgument(0);
                    ElectionSortDTO dto = new ElectionSortDTO();
                    dto.setElectionId(election.getElectionId());
                    dto.setElectionName(election.getElectionName());
                    dto.setElectionDate(election.getElectionDate());
                    dto.setTotalSeats(election.getTotalSeats());
                    dto.setElectionType(election.getElectionType());
                    dto.setElectionState(election.getElectionState());
                    return dto;
                });

        log.debug("Calling service method...");

        ElectionPageResponse response = electionService.getElectionsSorted("invalid", 0, 10);


        verify(electionRepository, times(1)).findAll(any(Pageable.class));
        log.info("Response: {}", response);

        assertNotNull(response, "Response should not be null");
        assertEquals(3, response.getTotalRecords(), "Total records should be 3");
        assertEquals(3, response.getElections().size(), "Election list should have 3 elements");

        assertEquals("Election 2", response.getElections().get(0).getElectionName());
        assertEquals("Election 1", response.getElections().get(1).getElectionName());
        assertEquals("Election 3", response.getElections().get(2).getElectionName());
    }

    @Test
    void deleteElectionById_Success() {
        log.info("Test: Deleting election successfully.");

        when(electionRepository.existsById(1L)).thenReturn(true);
        when(candidateRepository.existsByElection_ElectionId(1L)).thenReturn(false);

        ModelApiResponse response = electionService.deleteElectionById(1L);

        log.debug("Response: {}", response);

        verify(electionRepository, times(1)).deleteById(1L);
        assertNotNull(response);
        assertTrue(response.getSuccess());

        log.info("Test passed: Election deleted successfully.");
    }

    @Test
    void deleteElectionById_ElectionNotFound() {
        log.info("Test: Attempting to delete a non-existent election.");

        when(electionRepository.existsById(99L)).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> electionService.deleteElectionById(99L));

        verify(electionRepository, never()).deleteById(anyLong());

        log.warn("Test passed: Election not found, expected exception thrown.");
    }

    @Test
    void deleteElectionById_CandidatesExist() {
        log.info("Test: Attempting to delete an election with associated candidates.");

        when(electionRepository.existsById(1L)).thenReturn(true);
        when(candidateRepository.existsByElection_ElectionId(1L)).thenReturn(true);

        assertThrows(CandidateAssociatedException.class, () -> electionService.deleteElectionById(1L));

        verify(electionRepository, never()).deleteById(anyLong());

        log.warn("Test passed: CandidateAssociatedException thrown as expected.");
    }

    @Test
    void getAllElection_Success() {
        log.info("Testing getAllElection - Success Case");

        when(electionRepository.findAll()).thenReturn(List.of(election));
        when(globalMapper.toElection(any(Election.class))).thenReturn(electionDTO);

        ModelApiResponse response = electionService.getAllElection();

        log.debug("Response received: {}", response);

        verify(electionRepository,times(1)).findAll();
        assertTrue(response.getSuccess());
        assertEquals(1, ((List<ElectionDTO>) response.getData()).size());

        log.info("Test getAllElection_Success passed successfully");
    }

    @Test
    void getAllElection_NotFound() {
        log.info("Testing getAllElection - No Elections Found Case");

        when(electionRepository.findAll()).thenReturn(new ArrayList<>());

        verify(electionRepository,never()).findAll();
        assertThrows(DataNotFoundException.class, () -> {
            log.warn("Expected DataNotFoundException thrown for empty election list");
            electionService.getAllElection();
        });

        log.info("Test getAllElection_NotFound passed successfully");
    }

}
