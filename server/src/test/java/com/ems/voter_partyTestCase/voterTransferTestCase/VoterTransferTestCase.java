package voterTransferTestCase;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.CountyRepository;
import com.ems.repositories.TownRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.impls.VoterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.TransferAddress;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class VoterTransferTestCase {

    @Mock
    private Address existingAddress;

    private TransferAddress transferAddress;
    @Mock
    private VoterRepository voterRepository;

    @InjectMocks
    private VoterServiceImpl voterService;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private CountyRepository countyRepository;

    @Mock
    private TownRepository townRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private Address residential;
    private Address mailing;

    private Voter exitingVoter;

    @BeforeEach
    void setUp() {
        exitingVoter = new Voter();
        exitingVoter.setVoterId("000000010");

        transferAddress = new TransferAddress();
        transferAddress.setCounty("GOA");
        transferAddress.setTown("DOWNTOWN");
        transferAddress.setAddressType(TransferAddress.AddressTypeEnum.valueOf("RESIDENTIAL"));

        residential = new Address();
        residential.setAddressId(1L);
        residential.setCounty("Springfield");
        residential.setTown("abc");
        residential.setAddressType(AddressType.RESIDENTIAL);
        exitingVoter.setResidentialAddress(residential);

        mailing = new Address();
        mailing.setAddressId(1L);
        mailing.setTown("Las Vegas");
        mailing.setCounty("SpringFiled");
        mailing.setAddressType(AddressType.MAILING);
        exitingVoter.setMailingAddress(mailing);
    }


    @Test
    public void transferCountySuccessful() {

        String voterId = "000000011";
        transferAddress.setCounty("Springfield");
        transferAddress.setAddressType(TransferAddress.AddressTypeEnum.MAILING);
        when(voterRepository.findById(voterId)).thenReturn(Optional.of(exitingVoter));
        when(countyRepository.existsByCountyName(anyString())).thenReturn(false);
        assertThrows(DataNotFoundException.class, () -> voterService.transferVoterAddress(voterId, transferAddress), "Expected exception when county does not exist");

        verify(countyRepository, times(1)).existsByCountyName("Springfield");
        verify(townRepository, times(1)).existsByTownName("Springfield");
    }


    @Test
    public void transferTownSuccess() {
        log.info("Voter Town Transfer Success Test Case");

        String voterId = "000000013";
        TransferAddress transferAddress = new TransferAddress();
        transferAddress.setAddressType(TransferAddress.AddressTypeEnum.MAILING);

        lenient().when(voterRepository.findById(voterId)).thenReturn(Optional.of(exitingVoter));
        lenient().when(townRepository.existsByTownName(anyString())).thenReturn(false);

        assertThrows(DataNotFoundException.class, () -> voterService.transferVoterAddress(voterId, transferAddress), "Expected exception when county does not exist");
    }


    @Test
    public void transferTownAndCountySuccess() {
        log.info("Voter Town And County Transfer Success Test Case");
        TransferAddress transferAddress = new TransferAddress();
        transferAddress.setCounty("Las Vegas");
        transferAddress.setTown("SpringFiled");
        transferAddress.setAddressType(TransferAddress.AddressTypeEnum.valueOf("MAILING"));
        lenient().when(voterRepository.findById(exitingVoter.getVoterId())).thenReturn(Optional.ofNullable(exitingVoter));
        lenient().when(townRepository.existsByTownName(transferAddress.getTown())).thenReturn(false);
        lenient().when(countyRepository.existsByCountyName(transferAddress.getCounty())).thenReturn(false);
        voterService.transferVoterAddress(exitingVoter.getVoterId(), transferAddress);
        assertEquals("Voter Transferred Successfully","Voter Transferred Successfully");
        verify(voterRepository, times(1)).findById(exitingVoter.getVoterId());
        verify(townRepository, times(1)).existsByTownName(anyString());
        verify(countyRepository, times(1)).existsByCountyName(anyString());
    }

    @Test
    void testVoterNotFound_ShouldThrowException() {
        log.info("Test: Voter not found scenario");
        String voterId = "000000010";
        when(voterRepository.findById(voterId)).thenReturn(java.util.Optional.empty());
        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> voterService.transferVoterAddress(voterId, transferAddress));
        Assertions.assertEquals("Voter not found with voter id: " + voterId, exception.getMessage());
        verify(voterRepository, times(1)).findById(voterId);
    }

    @Test
    void testAddressTypeNotFound_ShouldThrowException() {
        log.info("Test: Address Type Not Found");
        exitingVoter.setVoterId("000000010");
        lenient().when(voterRepository.findById(exitingVoter.getVoterId())).thenReturn(Optional.of(exitingVoter));
        TransferAddress transferAddress = new TransferAddress();
        transferAddress.setAddressType(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> voterService.transferVoterAddress(exitingVoter.getVoterId(), transferAddress));
        assertEquals("AddressType cannot be null", exception.getMessage());
    }








}
