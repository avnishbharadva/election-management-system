package com.ems.partyTestCase.voterTransferTestCase;

import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.impls.PartyServiceImpl;
import com.ems.services.impls.VoterServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.PartyDTO;
import org.openapitools.model.TransferAddress;
import org.openapitools.model.VoterDTO;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PartyTestCase {

    @Mock
    private PartyServiceImpl partyService;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private Party party;

    @Mock
    private GlobalMapper globalMapper;

    @BeforeEach
    void setUp(){
        party.setPartyId(1L);
        party.setPartySymbol("SYM1");
        party.setPartyName("Bharat");
        party.setPartyAbbreviation("BH");
        party.setPartyWebSite("www.bh.com");
        party.setFounderName("John");
        party.setHeadQuarters("Mumbai");
        party.setPartyFoundationYear(2000);
        party.setCreatedAt(LocalDateTime.now());
        partyRepository.save(party);
    }

    @Test
    void testPartyRegistration(){
        log.info("Test: Party Registration");

        lenient().when(partyRepository.save(any(Party.class))).thenReturn(party);

        var partyDto = globalMapper.toPartyDTO(party);
        var registeredParty = partyService.saveParty(partyDto);


        assertNotNull(registeredParty);
        assertEquals("Bharat", registeredParty.getPartyName());
        assertEquals("SYM1", registeredParty.getPartySymbol());
        assertEquals("BH", registeredParty.getPartyAbbreviation());
        assertEquals("www.bh.com", registeredParty.getPartyWebSite());
        assertEquals("John", registeredParty.getFounderName());
        assertEquals("Mumbai", registeredParty.getHeadQuarters());
        assertEquals(2000, registeredParty.getPartyFoundationYear());
    }

}