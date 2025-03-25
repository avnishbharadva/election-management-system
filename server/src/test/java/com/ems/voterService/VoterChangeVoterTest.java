package com.ems.voterService;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.TownRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.impls.VoterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ChangeVoterAddress;
import org.openapitools.model.VoterDataDTO;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoterChangeVoterTest {

    @Mock
    private VoterRepository voterRepo;

    @Mock
    private AddressRepository addressRepo;

    @Mock
    private TownRepository townRepo;

    @Mock
    private GlobalMapper globalMapper;

    @InjectMocks
    private VoterServiceImpl voterService;
    private Voter existingVoter;
    private Address residentialAddress;
    private Address mailingAddress;
    private ChangeVoterAddress changeRequest;
    private VoterDataDTO voterDataDTO;

    @BeforeEach
    void setUp() {
        existingVoter = new Voter();
        existingVoter.setVoterId("123456789");

        residentialAddress = new Address();
        residentialAddress.setAddressId(1L);
        residentialAddress.setTown("Las Vegas");
        residentialAddress.setAddressType(AddressType.RESIDENTIAL);

        mailingAddress = new Address();
        mailingAddress.setAddressId(2L);
        mailingAddress.setTown("New York");
        mailingAddress.setAddressType(AddressType.MAILING);

        existingVoter.setResidentialAddress(residentialAddress);
        existingVoter.setMailingAddress(mailingAddress);

        changeRequest = new ChangeVoterAddress();
        changeRequest.setAddressType(ChangeVoterAddress.AddressTypeEnum.valueOf("RESIDENTIAL"));
        changeRequest.setTown("Miami");

        voterDataDTO = new VoterDataDTO();
    }

    @Test
    void voterNotFound() {
        when(voterRepo.findById(existingVoter.getVoterId())).thenReturn(Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest));
        assertEquals("Voter not found with voter id : 123456789", exception.getMessage());
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
        when(voterRepo.findById("V123")).thenReturn(Optional.of(existingVoter));
        when(townRepo.existsByTownName(changeRequest.getTown())).thenReturn(true);
        when(addressRepo.findById(mailingAddress.getAddressId())).thenReturn(Optional.of(mailingAddress));
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

        VoterDataDTO result = voterService.changeVoterAddress(existingVoter.getVoterId(), changeRequest);
        assertNotNull(result);
    }
}
