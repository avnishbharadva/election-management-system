package com.ems.services.impls;

import com.ems.controllers.PartyController;
import com.ems.entities.Party;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.repositories.PartyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.PartyDTO;
import org.openapitools.model.PartyDataDTO;
import org.openapitools.model.PartyRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Switch.CaseOperator.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PartyControllerTest {


    private MockMvc mockMvc;

    @InjectMocks
    private PartyController partyController;

    @Mock
    private PartyServiceImpl partyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private PartyRegisterDTO request;

    @Mock
    private PartyDataDTO responseData;

    @Mock
    private PartyDTO response;

    @Mock
    private Party party;

    @Mock
    private PartyRepository partyRepository;


    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(partyController).build();
        objectMapper = new ObjectMapper();

        String base64Image = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAB4AAwFK4pT9+AAAAAElFTkSuQmCC";
        request = new PartyRegisterDTO();
        request.setPartyWebSite("jygfyurfyudugyrgf");
        request.setPartyName("National CB");
        request.setHeadQuarters("BKC");
        request.setPartyLeaderName("Mohan Metar");
        request.setPartySymbol(base64Image);
        request.setPartyAbbreviation("NCB");
        request.setPartyFoundationYear(2011);
        request.setPartyFounderName("Mohan Lakhani");

        responseData = new PartyDataDTO();
        responseData.setPartyWebSite(request.getPartyWebSite());
        responseData.setPartyName(request.getPartyName());
        responseData.setHeadQuarters(request.getHeadQuarters());
        responseData.setPartyLeaderName(request.getPartyFounderName());
        responseData.setPartySymbol(request.getPartySymbol());
        responseData.setPartyAbbreviation(request.getPartyAbbreviation());
        responseData.setPartyFoundationYear(request.getPartyFoundationYear());
        responseData.setPartyFounderName(request.getPartyFounderName());

    }

    @Test
    void testCreateParty_Success() throws Exception {

        response.setMessage("Party registered successfully");
        response.setData(responseData);

        lenient().when(partyService.saveParty(any(PartyRegisterDTO.class))).thenReturn(responseData);
        mockMvc.perform(post("/party").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated());

    }

    @Test
    void testCreateParty_ValidationFailure() throws Exception {
        PartyRegisterDTO invalidRequest = new PartyRegisterDTO(); // Empty values

        mockMvc.perform(post("/party").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest))).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateParty_MissingRequiredFields() throws Exception {
        PartyRegisterDTO invalidRequest = new PartyRegisterDTO();
        invalidRequest.setPartyName("Valid Name"); // Other fields missing

        mockMvc.perform(post("/party").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(invalidRequest))).andExpect(status().isBadRequest());
    }

    @Test
    void testCreateParty_LongPartyName() throws Exception {
        request.setPartyName("A".repeat(100));

        mockMvc.perform(post("/party").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
    }

//    @Test
//    void testCreateParty_ServiceException() throws Exception {
//
//        Mockito.when(partyService.saveParty(any(PartyRegisterDTO.class)))
//                .thenThrow(new RuntimeException("Database error"));
//
//        mockMvc.perform(post("/party")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.message").value("Database error"));
//    }


}
