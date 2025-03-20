package com.ems.voterService;

import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.entities.VoterStatus;
import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.repositories.VoterStatusRepository;
import com.ems.services.impls.VoterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterUpdateRequest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoterUpdateTest {

    @Mock
    private VoterRepository voterRepo;

    @Mock
    private PartyRepository partyRepo;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private VoterStatusRepository voterStatusRepo;

    @InjectMocks
    private VoterServiceImpl voterService;

    private Voter existingVoter;
    private Party party;
    private VoterUpdateRequest updateRequest;
    private VoterUpdateRequest voterUpdateRequest;


    @BeforeEach
    void setUp() {
        voterUpdateRequest = new VoterUpdateRequest();
        voterUpdateRequest.setSsnNumber("123456789");
        voterUpdateRequest.setDmvNumber("DMV12345");
        voterUpdateRequest.setPhoneNumber("9876543210");
        voterUpdateRequest.setEmail("test@example.com");
        voterUpdateRequest.setPartyId(1L);
        voterUpdateRequest.setStatusId(1L);

        existingVoter = new Voter();
        existingVoter.setVoterId("123456789");
        existingVoter.setFirstName("John");

        party = new Party();
        party.setPartyId(2L);
        party.setPartyName("New Party");

        existingVoter.setParty(party);

        updateRequest = new VoterUpdateRequest();
        updateRequest.setFirstName("UpdatedJohn");
        updateRequest.setPartyId(2L);
    }

    @Test
    void updateVoter_VoterNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.updateVoter(existingVoter.getVoterId(), updateRequest));
        assertEquals("Voter not found with voter id: " + existingVoter.getVoterId(), exception.getMessage());
    }

    @Test
    void imageSaveFails() {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        lenient().when(partyRepo.findById(anyLong())).thenReturn(Optional.of(new Party()));
        lenient().when(voterStatusRepo.findById(anyLong())).thenReturn(Optional.of(new VoterStatus()));
        lenient().when(globalMapper.voterDTOtoVoter(any(VoterUpdateRequest.class), any(Voter.class)))
                .thenReturn(existingVoter);
        voterUpdateRequest.setImage("");
        CustomException exception = assertThrows(CustomException.class, () ->
                voterService.updateVoter(existingVoter.getVoterId(), voterUpdateRequest));
        assertEquals("Base64 string is empty or null", exception.getMessage());
    }

    @Test
    void signatureSaveFails() throws IOException {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        lenient().when(partyRepo.findById(anyLong())).thenReturn(Optional.of(new Party()));
        lenient().when(voterStatusRepo.findById(anyLong())).thenReturn(Optional.of(new VoterStatus()));
        lenient().when(globalMapper.voterDTOtoVoter(any(VoterUpdateRequest.class), any(Voter.class)))
                .thenReturn(existingVoter);
        voterUpdateRequest.setSignature("");
        CustomException exception = assertThrows(CustomException.class, () ->
                voterService.updateVoter(existingVoter.getVoterId(), voterUpdateRequest));
        assertEquals("Base64 string is empty or null", exception.getMessage());
    }

    @Test
    void updateVoter_Success() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(partyRepo.findById(updateRequest.getPartyId())).thenReturn(Optional.of(party));
        when(globalMapper.voterDTOtoVoter(updateRequest, existingVoter)).thenReturn(existingVoter);
        when(voterRepo.save(any(Voter.class))).thenReturn(existingVoter);

        VoterDataDTO updatedDTO = new VoterDataDTO();
        updatedDTO.setFirstName("UpdatedJohn");
        when(globalMapper.toVoterDTO(any())).thenReturn(updatedDTO);

        assertDoesNotThrow(() -> voterService.updateVoter(existingVoter.getVoterId(), updateRequest));
        verify(voterRepo, times(1)).save(any(Voter.class));
    }
}
