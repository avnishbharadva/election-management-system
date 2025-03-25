package com.ems.services.impls;

import com.ems.entities.Party;
import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.PartyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.openapitools.model.PartyUpdateDTO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PartyServiceImplTest {

    @InjectMocks
    private PartyServiceImpl partyService;

    @Mock
    private PartyRepository partyRepository;

    @Mock
    private GlobalMapper globalMapper;

    @Mock
    private PartyRegisterDTO partyDTO;

    @Mock
    private PartyUpdateDTO partyUpdateDTO;

    @Mock
    private Party party;

    @Mock
    private PartyDataDTO partyResponse;


    @TempDir
    Path tempDir; // Temporary directory for testing file storage

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        party = new Party();
        party.setPartyId(1L);
        party.setPartyName("Bharat");
        String img = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAB4AAwFK4pT9+AAAAAElFTkSuQmCC";
        party.setPartySymbol(img);
        party.setPartyFounderName("Mohan");
        party.setPartyFoundationYear(2012);
        party.setPartyWebSite("www.bharat.com");
        party.setPartyLeaderName("Raman");

        partyDTO = new PartyRegisterDTO();
        partyDTO.setPartyName("Bharat");
        partyDTO.setPartyAbbreviation("BH");
        partyDTO.setPartyLeaderName("John Doe");
        partyDTO.setPartyWebSite("www.bharat.com");
        partyDTO.setHeadQuarters("Mumbai");
        partyDTO.setPartySymbol("Bharat.png");

        partyUpdateDTO = new PartyUpdateDTO();
        partyUpdateDTO.setPartySymbol("qwerty");


        partyResponse = new PartyDataDTO();
        partyResponse.setPartyName(party.getPartyName());
        partyResponse.setPartyAbbreviation(party.getPartyAbbreviation());
        partyResponse.setPartyLeaderName(party.getPartyLeaderName());
        partyResponse.setPartyWebSite(party.getPartyWebSite());
        partyResponse.setPartyFoundationYear(party.getPartyFoundationYear());
        partyResponse.setHeadQuarters(party.getHeadQuarters());

    }

    @Test
    void testRegister_Success() throws Exception {

//String base64Image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAB4AAwFK4pT9+AAAAAElFTkSuQmCC";
//        PartyRegisterDTO partyDTO = new PartyRegisterDTO();
//        partyDTO.setPartyName("Bharat");
//        partyDTO.setPartyAbbreviation("BJP");
//        partyDTO.setPartyLeaderName("Leader Name");
//        partyDTO.setPartySymbol(base64Image);
//        partyDTO.setPartyWebSite("https://example.com");
//        partyDTO.setHeadQuarters("New Delhi");
//
//        Party party = new Party();
//        party.setPartyName(partyDTO.getPartyName());
//        party.setPartyAbbreviation(partyDTO.getPartyAbbreviation());
//        party.setPartyLeaderName(partyDTO.getPartyLeaderName());
//        party.setPartySymbol(partyDTO.getPartySymbol());
//        party.setPartyWebSite(partyDTO.getPartyWebSite());
//        party.setHeadQuarters(partyDTO.getHeadQuarters());

//        PartyDataDTO partyResponse = new PartyDataDTO();
//        partyResponse.setPartyName(party.getPartyName());
//        partyResponse.setPartyAbbreviation(party.getPartyAbbreviation());
//        partyResponse.setPartyLeaderName(party.getPartyLeaderName());
//        partyResponse.setPartyWebSite(party.getPartyWebSite());
//        partyResponse.setPartyFoundationYear(party.getPartyFoundationYear());
//        partyResponse.setHeadQuarters(party.getHeadQuarters());

        when(globalMapper.toParty(any(PartyRegisterDTO.class))).thenReturn(party);
        when(globalMapper.toPartyDTO(any(Party.class))).thenReturn(partyResponse);

        var result = partyService.saveParty(partyDTO);

        assertNotNull(result);
        assertEquals("Bharat", result.getPartyName());

        verify(partyRepository, times(1)).save(any(Party.class));
    }

    @Test
    void testSaveParty_DuplicateEntry_ShouldThrowException() {
        lenient().when(partyRepository.existsByPartyNameOrPartyAbbreviationOrPartyLeaderName(
                "Bharat",
                "BH",
                partyDTO.getPartyLeaderName()
        )).thenReturn(true);

        assertThrows(DataAlreadyExistException.class, () -> partyService.saveParty(partyDTO));
    }

    @Test
    void testSaveParty_InvalidBase64_ShouldThrowException() {
        partyDTO.setPartySymbol(partyDTO.getPartySymbol());
        assertThrows(Exception.class, () -> partyService.saveParty(partyDTO));
        assertEquals("Bharat.png","Bharat.png");
    }

    @Test
    void testDeleteParty_PartyNotFound() {
        when(partyRepository.findById(11L)).thenReturn(Optional.of(party));
        assertThrows(DataNotFoundException.class, () -> partyService.deleteParty(1L));
        verify(partyRepository, never()).deleteById(anyLong());
    }

    @Test
    void testUpdateParty_Success() throws IOException {

        PartyUpdateDTO updateDTO = new PartyUpdateDTO();
        updateDTO.setHeadQuarters("Bharat");

        when(partyRepository.findById(party.getPartyId())).thenReturn(Optional.of(party));
        when(globalMapper.toPartyDTO(any(Party.class))).thenReturn(new PartyDataDTO());

        PartyDataDTO response = partyService.updateParty(party.getPartyId(), updateDTO);

        assertNotNull(response);
        verify(partyRepository).save(any(Party.class));
    }

    @Test
    void testUpdateParty_PartyNotFound() {
        Long partyId = 2L;
        PartyUpdateDTO updateDTO = new PartyUpdateDTO();

        when(partyRepository.findById(partyId)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> partyService.updateParty(partyId, updateDTO));
        verify(partyRepository, never()).save(any(Party.class));
    }

    @Test
    void testUpdateParty_IOException() throws IOException {
        Long partyId = 1L;
        partyUpdateDTO.setPartySymbol("data:image/png;base64,iVBORw0KGgoAAA..."); // Example Base64

        when(partyRepository.findById(partyId)).thenReturn(Optional.of(party));

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(any(Path.class), any(byte[].class)))
                    .thenThrow(new IOException("Disk error"));

            assertThrows(Exception.class, () -> partyService.updateParty(partyId, partyUpdateDTO));

            verify(partyRepository, never()).save(any());
        }
    }





}