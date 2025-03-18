package com.ems.services.impls;

import com.ems.entities.Election;
import com.ems.exceptions.DataNotFoundException;
import com.ems.repositories.ElectionRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ElectionServiceImplTest {

    private final ElectionServiceImpl electionService;

    private final ElectionRepository electionRepository;

    private static Long electionId;

    @BeforeAll
    static void setup(@Autowired ElectionRepository electionRepository) {
        Election election = new Election();
        election.setElectionName("Local Elections");
        election.setElectionType("State");
        election.setElectionDate(LocalDate.of(2025, 11, 9));
        election.setElectionState("New York");
        election.setTotalSeats(50);

        Election savedElection = electionRepository.save(election);
        electionId = savedElection.getElectionId();
    }

    @Test
    @Order(1)
    void testGetElectionById_Success() {
        var response = electionService.getElectionById(electionId);
        assertTrue(response.getSuccess());
    }

    @Test
    @Order(2)
    void testGetElectionById_NotFound() {
        Long invalidId = 123L;
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            electionService.getElectionById(invalidId);
        });
        assertEquals("No election found with id:" + invalidId, exception.getMessage());
    }

    @Test
    @Order(3)
    void testDeleteElectionById_Success() {
        Long newElectionId = electionRepository.save(new Election()).getElectionId();
        electionService.deleteElectionById(newElectionId);
        Optional<Election> deletedElection = electionRepository.findById(newElectionId);
        assertTrue(deletedElection.isEmpty());
    }

    @Test
    @Order(4)
    void testDeleteElectionById_NotFound() {
        Long invalidElectionId = 9999L;
        Exception exception = assertThrows(DataNotFoundException.class, () -> {
            electionService.deleteElectionById(invalidElectionId);
        });

        assertEquals("No election found with id:" + invalidElectionId, exception.getMessage());
    }

    @AfterEach
     void cleanup() {
        electionRepository.deleteById(electionId);
    }
}
