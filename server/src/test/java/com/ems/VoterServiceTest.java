package com.ems;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.entities.VoterStatus;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.impls.VoterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.ChangeVoterAddress;
import org.openapitools.model.VoterDataDTO;


import org.springframework.data.domain.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoterServiceTest {


    @Mock private VoterRepository voterRepo;
    @Mock private PartyRepository partyRepo;
    @Mock private AddressRepository addressRepo;
    @Mock private VoterStatusRepository voterStatusRepo;
    @Mock private TownRepository townRepo;
    @Mock private GlobalMapper globalMapper;

    @InjectMocks private VoterServiceImpl voterService;

    private Voter existingVoter;
    private Party party;
    private VoterStatus voterStatus;
    private Address residentialAddress, mailingAddress;
    private VoterRegisterDTO voterRegisterDTO;
    private VoterUpdateRequest updateRequest;
    private ChangeVoterAddress changeRequest;
    private VoterDataDTO expectedResponse;
    private VoterDataDTO voterDataDTO;

    private List<Voter> voterList;
    private Page<Voter> voterPage;

    @BeforeEach
    void setUp() {
        existingVoter = new Voter();
        existingVoter.setVoterId("123456789");

        residentialAddress = new Address();
        residentialAddress.setAddressId(1L);
        residentialAddress.setTown("Las Vegas");
        residentialAddress.setAddressType(AddressType.RESIDENTIAL);
        existingVoter.setResidentialAddress(residentialAddress);

        mailingAddress = new Address();
        mailingAddress.setAddressId(2L);
        mailingAddress.setTown("New York");
        mailingAddress.setAddressType(AddressType.MAILING);
        existingVoter.setMailingAddress(mailingAddress);

        voterRegisterDTO = new VoterRegisterDTO();
        voterRegisterDTO.setSsnNumber("123456789");
        voterRegisterDTO.setDmvNumber("DMV12345");
        voterRegisterDTO.setPhoneNumber("9876543210");
        voterRegisterDTO.setEmail("test@example.com");
        voterRegisterDTO.setImage("data:image/png;base64,iVBsdbbfesfshdbvfesvfvjcsdbhdsbhvghgyjyORw0KGgoAAAANSUhEUgAA...");
        voterRegisterDTO.setSignature("data:image/png;base64,iVBORw0KGgfchctdgrxgxvdfbfjcthhffjftjoAAAANSUhEUgAA...");
        voterRegisterDTO.setPartyId(1L);
        voterRegisterDTO.setStatusId(1L);

        updateRequest = new VoterUpdateRequest();
        updateRequest.setFirstName("UpdatedJohn");
        updateRequest.setPartyId(2L);

        changeRequest = new ChangeVoterAddress();
        changeRequest.setAddressType(ChangeVoterAddress.AddressTypeEnum.RESIDENTIAL);
        changeRequest.setTown("Miami");

        party = new Party();
        party.setPartyId(1L);
        party.setPartyName("Demo Party");
        party.setPartySymbol("data:image/png;base64,iVBsdbbfesfshdbvfesvfvjcsdbhdsbhvghgyjyORw0KGgoAAAANSUhEUgAA...");
        existingVoter.setParty(party);

        voterStatus = new VoterStatus();
        voterStatus.setStatusId(1L);
        voterStatus.setStatusDesc("Active");

        voterDataDTO = new VoterDataDTO();
        expectedResponse = new VoterDataDTO();
        expectedResponse.setSsnNumber(voterRegisterDTO.getSsnNumber());
        expectedResponse.setDmvNumber(voterRegisterDTO.getDmvNumber());
        expectedResponse.setPhoneNumber(voterRegisterDTO.getPhoneNumber());
        expectedResponse.setEmail(voterRegisterDTO.getEmail());
        expectedResponse.setParty(party.getPartyName());
        expectedResponse.setStatus(voterStatus.getStatusDesc());
        expectedResponse.setImage("data:image/png;base64,iVBsdbbfesfshdbvfesvfvjcsdbhdsbhvghgyjyORw0KGgoAAAANSUhEUgAA...");
        expectedResponse.setSignature("data:image/png;base64,iVBORw0KGgfchctdgrxgxvdfbfjcthhffjftjoAAAANSUhEUgAA...");

        var voter1 = new Voter();
        var voter2 = new Voter();

        voterList = List.of(voter1 , voter2);
        voterPage = new PageImpl<>(voterList);
    }

    // ------------------------ Voter Address Change Tests ------------------------

    @Test
    void voterNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Voter not found with voter id : 123456789", exception.getMessage());
    }

    @Test
    void voterFound() {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        assertEquals("123456789", existingVoter.getVoterId());
    }

    @Test
    void townNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(false);
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Town Not Found With TownID : " + changeRequest.getTown(), exception.getMessage());
    }

    @Test
    void townFound() {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        lenient().when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        assertEquals("Miami" , changeRequest.getTown());
    }

    @Test
    void residentialAddressNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        when(addressRepo.findById(residentialAddress.getAddressId())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Address Not Found For VoterId : 123456789", exception.getMessage());
    }

    @Test
    void mailingAddressNotFound() {
        changeRequest.setAddressType(ChangeVoterAddress.AddressTypeEnum.valueOf("MAILING"));
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        when(addressRepo.findById(mailingAddress.getAddressId())).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Address Not Found For VoterId : 123456789", exception.getMessage());
    }

    @Test
    void addressSaveFails() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        when(addressRepo.findById(residentialAddress.getAddressId())).thenReturn(Optional.of(residentialAddress));
        when(globalMapper.changeAddressDTOToAddress(any(), any())).thenReturn(new Address());

        changeRequest.setAddressType(ChangeVoterAddress.AddressTypeEnum.RESIDENTIAL);
        doThrow(new RuntimeException("Database save error")).when(addressRepo).save(any());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Database save error", exception.getMessage());
    }

    @Test
    void address_Success() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        when(addressRepo.findById(anyLong())).thenReturn(Optional.of(new Address()));
        when(globalMapper.changeAddressDTOToAddress(any(), any())).thenReturn(new Address());
        when(globalMapper.toVoterDTO(any())).thenReturn(voterDataDTO);
        assertDoesNotThrow(() -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
    }

    // ------------------------ Voter Registration Tests ------------------------

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
        when(globalMapper.toVoter(any(org.openapitools.model.VoterRegisterDTO.class))).thenReturn(existingVoter);
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
        when(globalMapper.toVoter(voterRegisterDTO)).thenReturn(existingVoter);
        when(voterStatusRepo.findById(voterRegisterDTO.getStatusId())).thenReturn(Optional.of(voterStatus));
        when(globalMapper.toAddress(voterRegisterDTO.getResidentialAddress())).thenReturn(residentialAddress);
        when(globalMapper.toAddress(voterRegisterDTO.getMailingAddress())).thenReturn(mailingAddress);
        when(voterRepo.save(any(Voter.class))).thenReturn(existingVoter);
        when(addressRepo.saveAll(anyList())).thenReturn(null);
        when(globalMapper.toVoterDTO(any(Voter.class))).thenReturn(expectedResponse);
        System.out.println("Expected Response: " + expectedResponse);

        VoterDataDTO register = voterService.register(voterRegisterDTO);

        assertDoesNotThrow(() -> voterService.register(voterRegisterDTO));
        assertNotNull(expectedResponse, "expectedResponse should not be null");

        assertNotNull(register, "register should not be null");
        System.out.println("Expected register: " + register);

        assertEquals(expectedResponse, register);

        verify(voterRepo, times(2)).save(any(Voter.class));
        verify(addressRepo, times(2)).saveAll(anyList());
        verify(globalMapper, times(2)).toVoterDTO(any(Voter.class));

    }

    // ------------------------ Voter Update Tests ------------------------

    @Test
    void updateVoter_VoterNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.updateVoter(existingVoter.getVoterId(), updateRequest));
        assertEquals("Voter not found with voter id: " + existingVoter.getVoterId(), exception.getMessage());
    }

    @Test
    void imageSaveFailsUpdate() {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        lenient().when(partyRepo.findById(anyLong())).thenReturn(Optional.of(new Party()));
        lenient().when(voterStatusRepo.findById(anyLong())).thenReturn(Optional.of(new VoterStatus()));
        lenient().when(globalMapper.voterDTOtoVoter(any(VoterUpdateRequest.class), any(Voter.class)))
                .thenReturn(existingVoter);
        updateRequest.setImage("");
        CustomException exception = assertThrows(CustomException.class, () ->
                voterService.updateVoter(existingVoter.getVoterId(), updateRequest));
        assertEquals("Invalid Base64 image format", exception.getMessage());
    }

    @Test
    void signatureSaveFailsUpdate() throws IOException {
        lenient().when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.of(existingVoter));
        lenient().when(partyRepo.findById(anyLong())).thenReturn(Optional.of(new Party()));
        lenient().when(voterStatusRepo.findById(anyLong())).thenReturn(Optional.of(new VoterStatus()));
        lenient().when(globalMapper.voterDTOtoVoter(any(VoterUpdateRequest.class), any(Voter.class)))
                .thenReturn(existingVoter);
        updateRequest.setSignature("");
        CustomException exception = assertThrows(CustomException.class, () ->
                voterService.updateVoter(existingVoter.getVoterId(), updateRequest));
        assertEquals("Invalid Base64 image format", exception.getMessage());
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

    // ------------------------ Voter Search Tests ------------------------

    @Test
    void searchWithValidArguments() {
        var searchDTO = new VoterSearchDTO("John",null, LocalDate.of(1990, 1, 1), "DMV123", "SSN123", "New York");
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "firstName"));
        when(voterRepo.searchVoters( searchDTO.getFirstName(), searchDTO.getLastName(), searchDTO.getDateOfBirth(), searchDTO.getDmvNumber(), searchDTO.getSsnNumber(), searchDTO.getCity(), pageable)).thenReturn(voterPage);

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
