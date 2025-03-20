package com.ems.voterService;

import com.ems.dtos.ErrorResponse;
import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.entities.VoterStatus;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.repositories.VoterStatusRepository;
import com.ems.services.impls.VoterServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoterRegistrationTest {

    @Mock
    private VoterRepository voterRepo;
    @Mock
    private PartyRepository partyRepo;
    @Mock
    private AddressRepository addressRepo;
    @Mock
    private VoterStatusRepository voterStatusRepo;
    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private VoterServiceImpl voterService;

    @Mock
    private Validator validator;
    @Mock
    private ErrorResponse errorResponse;

    private VoterRegisterDTO voterRegisterDTO;
    private Voter voter;
    private Party party;
    private VoterStatus voterStatus;
    private Address residentialAddress;
    private Address mailingAddress;
    private VoterDataDTO expectedResponse;


    @BeforeEach
    void setUp() {
        voterRegisterDTO = new VoterRegisterDTO();
        voterRegisterDTO.setSsnNumber("123456789");
        voterRegisterDTO.setDmvNumber("DMV12345");
        voterRegisterDTO.setPhoneNumber("9876543210");
        voterRegisterDTO.setEmail("test@example.com");
        voterRegisterDTO.setPartyId(1L);
        voterRegisterDTO.setStatusId(1L);

        residentialAddress = new Address();
        residentialAddress.setAddressType(AddressType.RESIDENTIAL);

        mailingAddress = new Address();
        mailingAddress.setAddressType(AddressType.MAILING);

        voter = new Voter();
        voter.setSsnNumber(voterRegisterDTO.getSsnNumber());
        voter.setDmvNumber(voterRegisterDTO.getDmvNumber());
        voter.setPhoneNumber(voterRegisterDTO.getPhoneNumber());
        voter.setEmail(voterRegisterDTO.getEmail());

        party = new Party();
        party.setPartyId(1L);
        party.setPartyName("Demo Party");
        voter.setParty(party);

        voterStatus = new VoterStatus();
        voterStatus.setStatusId(1L);
        voterStatus.setStatusDesc("Active");
        voter.setVoterStatus(voterStatus);

        VoterDataDTO expectedResponse = new VoterDataDTO();
        expectedResponse.setSsnNumber(voterRegisterDTO.getSsnNumber());
        expectedResponse.setDmvNumber(voterRegisterDTO.getDmvNumber());
        expectedResponse.setPhoneNumber(voterRegisterDTO.getPhoneNumber());
        expectedResponse.setEmail(voterRegisterDTO.getEmail());
        expectedResponse.setPartyId(party.getPartyId());
        expectedResponse.setStatusId(voterStatus.getStatusId());
    }

    @Test
    void voterExistsByEmail() {
        when(voterRepo.existsByEmail(voterRegisterDTO.getEmail())).thenReturn(true);
        var emailCaptor = ArgumentCaptor.forClass(String.class);
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class, () ->
                voterService.register(voterRegisterDTO));

        verify(voterRepo).existsByEmail(emailCaptor.capture());
        assertEquals(voterRegisterDTO.getEmail(), emailCaptor.getValue());
        assertEquals("Voter Already Exist with Email : " + voterRegisterDTO.getEmail(), exception.getMessage());
        verify(voterRepo, times(1)).existsByEmail(voterRegisterDTO.getEmail());
    }

    @Test
    void voterAlreadyExistsBySsn() {
        when(voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber())).thenReturn(true);
        var ssnCaptor = ArgumentCaptor.forClass(String.class);
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class,
                () -> voterService.register(voterRegisterDTO));

        verify(voterRepo).existsBySsnNumber(ssnCaptor.capture());
        assertEquals(voterRegisterDTO.getSsnNumber(), ssnCaptor.getValue());
        assertEquals("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber(), exception.getMessage());
        verify(voterRepo, times(1)).existsBySsnNumber(voterRegisterDTO.getSsnNumber());
    }

    @Test
    void voterExistsByDMV() {
        when(voterRepo.existsByDmvNumber(voterRegisterDTO.getDmvNumber())).thenReturn(true);
        var dmvCaptor = ArgumentCaptor.forClass(String.class);
        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class, () ->
                voterService.register(voterRegisterDTO));

        verify(voterRepo).existsByDmvNumber(dmvCaptor.capture());
        assertEquals(voterRegisterDTO.getDmvNumber(), dmvCaptor.getValue());
        assertEquals("Voter Already Exist with DMV Number : " + voterRegisterDTO.getDmvNumber(), exception.getMessage());
        verify(voterRepo, times(1)).existsByDmvNumber(voterRegisterDTO.getDmvNumber());
    }

    @Test
    void voterExistsByPhone() {
        when(voterRepo.existsByPhoneNumber(voterRegisterDTO.getPhoneNumber())).thenReturn(true);
        var phoneNumber = ArgumentCaptor.forClass(String.class);

        DataAlreadyExistException exception = assertThrows(DataAlreadyExistException.class, () ->
                voterService.register(voterRegisterDTO));

        verify(voterRepo, times(1)).existsByPhoneNumber(phoneNumber.capture());
        assertEquals(voterRegisterDTO.getPhoneNumber(), phoneNumber.getValue());
        assertEquals("Voter Already Exist with Phone Number : " + voterRegisterDTO.getPhoneNumber(), exception.getMessage());
        verify(voterRepo, times(1)).existsByPhoneNumber(voterRegisterDTO.getPhoneNumber());
    }

    @Test
    void partyNotFound() {
        when(partyRepo.findById(voterRegisterDTO.getPartyId())).thenReturn(Optional.empty());
        var partyIdCaptor = ArgumentCaptor.forClass(Long.class);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                voterService.register(voterRegisterDTO));

        verify(partyRepo, times(1)).findById(partyIdCaptor.capture());
        assertEquals(voterRegisterDTO.getPartyId(), partyIdCaptor.getValue());

        assertEquals("Party Not Found with ID : " + voterRegisterDTO.getPartyId(), exception.getMessage());
        verify(partyRepo, times(1)).findById(voterRegisterDTO.getPartyId());
    }

    @Test
    void voterStatusNotFound() {
        when(globalMapper.toVoter(any(VoterRegisterDTO.class))).thenReturn(voter);
        when(partyRepo.findById(voterRegisterDTO.getPartyId())).thenReturn(Optional.of(party));
        when(voterStatusRepo.findById(voterRegisterDTO.getStatusId())).thenReturn(Optional.empty());

        Optional<Party> actualParty = partyRepo.findById(voterRegisterDTO.getPartyId());
        assertTrue(actualParty.isPresent(), "Party should be present in the repository");
        assertSame(party, actualParty.get(), "Returned party should be the same as the expected one");

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () ->
                voterService.register(voterRegisterDTO));

        verify(globalMapper, times(1)).toVoter(any(VoterRegisterDTO.class));
        verify(partyRepo, times(2)).findById(anyLong());
        verify(voterStatusRepo, times(1)).findById(voterRegisterDTO.getStatusId());

        assertEquals("Voter Status Not Found", exception.getMessage());
    }


    @Test
    void voterSuccess() {
        when(voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber())).thenReturn(false);
        when(voterRepo.existsByDmvNumber(voterRegisterDTO.getDmvNumber())).thenReturn(false);
        when(voterRepo.existsByPhoneNumber(voterRegisterDTO.getPhoneNumber())).thenReturn(false);
        when(voterRepo.existsByEmail(voterRegisterDTO.getEmail())).thenReturn(false);
        when(partyRepo.findById(voterRegisterDTO.getPartyId())).thenReturn(Optional.of(party));
        when(globalMapper.toVoter(voterRegisterDTO)).thenReturn(voter);
        when(voterStatusRepo.findById(voterRegisterDTO.getStatusId())).thenReturn(Optional.of(voterStatus));
        when(globalMapper.toAddress(voterRegisterDTO.getResidentialAddress())).thenReturn(residentialAddress);
        when(globalMapper.toAddress(voterRegisterDTO.getMailingAddress())).thenReturn(mailingAddress);
        when(voterRepo.save(any(Voter.class))).thenReturn(voter);
        when(addressRepo.saveAll(anyList())).thenReturn(null);
        when(globalMapper.toVoterDTO(any(Voter.class))).thenReturn(expectedResponse);

        VoterDataDTO register = voterService.register(voterRegisterDTO);

        assertDoesNotThrow(() -> voterService.register(voterRegisterDTO));
        assertEquals(expectedResponse, register);

        verify(voterRepo, times(2)).save(any(Voter.class));
        verify(addressRepo, times(2)).saveAll(anyList());
        verify(globalMapper, times(2)).toVoterDTO(any(Voter.class));

    }
}