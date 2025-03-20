package com.ems.voterService;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.VoterRepository;
import com.ems.services.impls.VoterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.VoterDataDTO;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VoterServiceSearchTest {

    @Mock
    private VoterRepository voterRepo;

    @Mock
    private Voter existingVoter;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private VoterServiceImpl voterService;

    private List<Voter> voterList;
    private Page<Voter> voterPage;

    @BeforeEach
    void setUp() {
        existingVoter = new Voter();
        var voter1 = new Voter();
        var voter2 = new Voter();
        existingVoter.setVoterId("123456789");

        var mailingAddress = new Address();
        var residentialAddress = new Address();
        mailingAddress.setCity("New York");
        residentialAddress.setCity("Las Vegas");
        existingVoter.setResidentialAddress(residentialAddress);
        existingVoter.setMailingAddress(mailingAddress);

        voterList = List.of(voter1 , voter2);
        voterPage = new PageImpl<>(voterList);
    }

    @Test
    void searchWithValidArguments() {
        var searchDTO = new VoterSearchDTO("John",null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));
        when(voterRepo.searchVoters( "John", null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York", pageable)).thenReturn(voterPage);

        String[] sort = {"firstName", "asc"};
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchWithEmptySortArguments() {
        var searchDTO = new VoterSearchDTO("John", "Doe", LocalDate.of(1990, 1, 1), "DMV12345", "SSN98765", "New York");
        Pageable pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(voterRepo.searchVoters( "John", "Doe", LocalDate.of(1990, 1, 1), "DMV12345", "SSN98765", "New York", pageable)).thenReturn(voterPage);

        String[] sort = null;
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchWithNoMatchingArguments() {
        var searchDTO = new VoterSearchDTO("Nonexistent", "User", null, null, null, null);
        var pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "lastName"));
        when(voterRepo.searchVoters( "Nonexistent", "User", null, null, null, null ,pageable)).thenReturn(Page.empty());

        String[] sort = {"lastName", "desc"};
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertTrue(result.isEmpty());
    }

    @Test
    void searchWithInvalidSortParams() {
        var searchDTO = new VoterSearchDTO("John", null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "invalidField"));
        when(voterRepo.searchVoters( "John", null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York" , pageable)).thenReturn(Page.empty());

        String[] sort = {"invalidField", "invalidDirection"};
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());
    }

    @Test
    void searchWithOnlyPagination() {
        var searchDTO = new VoterSearchDTO(null, null, null, null, null, null);
        Pageable pageable = PageRequest.of(0, 10 , Sort.by(Sort.Direction.DESC, "lastName"));
        when(voterRepo.searchVoters(searchDTO.getFirstName(), searchDTO.getLastName(), searchDTO.getDateOfBirth(), searchDTO.getDmvNumber(), searchDTO.getSsnNumber(), searchDTO.getCity(), pageable)).thenReturn(voterPage);

        String[] sort = {"lastName", "desc"};
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void searchWithNullSorting() {
        var searchDTO = new VoterSearchDTO("John",null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York");
        var pageable = PageRequest.of(0, 10, Sort.unsorted());
        when(voterRepo.searchVoters( searchDTO.getFirstName(), searchDTO.getLastName(), searchDTO.getDateOfBirth(), searchDTO.getDmvNumber(), searchDTO.getSsnNumber(), searchDTO.getCity(), pageable)).thenReturn(voterPage);

        String[] sort = null;
        Page<VoterDataDTO> result = voterService.searchVoters(searchDTO, 0, 10, sort);

        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
    }
}
