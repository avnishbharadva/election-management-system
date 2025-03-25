package com.ems.controllers;

import com.ems.services.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.openapitools.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@WebMvcTest(CandidateController.class)
class ErrorItemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidateService candidateService;

    private CandidateDto candidateDto;
    private CandidateName candidateName;
    private CandidateAddress residentialAddress;
    private CandidateAddress mailingAddress;
    private BankDetails bankDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        candidateDto = new CandidateDto();
        candidateName = new CandidateName();
        candidateName.setFirstName("John");
        candidateName.setMiddleName("A.");
        candidateName.setLastName("Doe");

        residentialAddress = new CandidateAddress();
        residentialAddress.setAddressId(1L);
        residentialAddress.setStreet("123 Main St");
        residentialAddress.setCity("New York");
        residentialAddress.setZipcode(10001);

        mailingAddress = new CandidateAddress();
        mailingAddress.setAddressId(2L);
        mailingAddress.setStreet("456 Elm St");
        mailingAddress.setCity("Los Angeles");
        mailingAddress.setZipcode(90001);

        bankDetails = new BankDetails();
        bankDetails.setBankDetailsId(10L);
        bankDetails.setBankName("Bank of America");
        bankDetails.setBankAddress("123 Wall Street, New York, NY");

        candidateDto.setCandidateName(candidateName);
        candidateDto.setCandidateSSN("1234567"); // Incorrect SSN to trigger validation error
        candidateDto.setDateOfBirth(LocalDate.of(1990, 5, 15));
        candidateDto.setGender(Gender.MALE);
        candidateDto.setMaritalStatus(MaritalStatus.MARRIED);
        candidateDto.setNoOfChildren(2);
        candidateDto.setSpouseName("Jane Doe");
        candidateDto.setPartyId(101L);
        candidateDto.setResidentialAddress(residentialAddress);
        candidateDto.setMailingAddress(mailingAddress);
        candidateDto.setStateName("California");
        candidateDto.setCandidateEmail("candidate@example.com");
        candidateDto.setElectionId(3001L);
        candidateDto.setElectionName("Presidential Election 2024");
        candidateDto.setPartyName("Democratic Party");
        candidateDto.setBankDetails(bankDetails);
        candidateDto.setCandidateImage("/images/candidate123.jpg");
        candidateDto.setCandidateSignature("/signatures/candidate123.png");
    }

    @Test
    void testHandleValidationException() throws Exception {
//        mockMvc.perform(post("/candidates")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(candidateDto)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.message").value("Bad request, validation failed for fields"))
//                .andExpect(jsonPath("$.errors[?(@.field=='candidateSSN')].errorMessage").value("SSN must contain exactly 9 digits"));
//
        mockMvc.perform(MockMvcRequestBuilders.post("/candidates"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Bad request, validation failed for fields"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[?(@.field=='candidateSSN')].errorMessage").value("SSN must contain exactly 9 digits"));
    }
}