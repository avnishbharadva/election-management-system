package com.ems;

import com.ems.controllers.VoterApiController;
import com.ems.services.VoterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.AddressDTO;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class VoterApiControllerTest {


    @InjectMocks
    private VoterApiController voterApiController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private VoterService voterService;

    private VoterRegisterDTO validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new VoterRegisterDTO();
        validRequest.setFirstName("John");
        validRequest.setLastName("Doe");
        validRequest.setDateOfBirth(LocalDate.of(1990, 5, 15));
        validRequest.setGender(VoterRegisterDTO.GenderEnum.MALE);
        validRequest.setDmvNumber("123456789");
        validRequest.setSsnNumber("987654321");
        validRequest.setEmail("john.doe@example.com");
        validRequest.setPhoneNumber("12345678901");

        var residentialAddress = new AddressDTO();
        residentialAddress.setTown("Las Vegas");
        validRequest.setResidentialAddress(residentialAddress);

        var mailingAddress = new AddressDTO();
        mailingAddress.setTown("New York");
        validRequest.setMailingAddress(mailingAddress);

        var mockVoterDTO = new VoterDTO();
        mockVoterDTO.setMessage("Voter Registered Successfully");

        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(voterApiController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Test
    void registerVoter_Success() throws Exception {
        log.info("Starting registerVoter_Success test");
        lenient().when(voterService.register(any(VoterRegisterDTO.class)))
                .thenReturn(new org.openapitools.model.VoterDataDTO());
        log.info("voterService.register method called");

        var response = voterApiController.registerVoter(validRequest);
        log.info("voterApiController.registerVoter method called");
        assertNotNull(response.getBody());
        assertEquals("Voter Registered Successfully", Objects.requireNonNull(response.getBody()).getMessage());

        mockMvc.perform(post("/voters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Voter Registered Successfully"));
        log.info("Finished registerVoter_Success test");
    }

    @Test
    void registerVoterWithEmptyDTO() {
        log.info("Starting registerVoterWithEmptyDTO test");
        VoterRegisterDTO emptyDTO = new VoterRegisterDTO();

        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(emptyDTO);

        for (ConstraintViolation<VoterRegisterDTO> violation : violations) {
            log.error("{}: {}", violation.getPropertyPath(), violation.getMessage());
        }
        assertFalse(violations.isEmpty(), "Validation should fail for an empty DTO");
        assertEquals(9, violations.size(), "Expected 9 validation errors for empty DTO");

        Set<String> expectedFields = Set.of(
                "firstName", "lastName", "dateOfBirth", "gender",
                "dmvNumber", "ssnNumber", "email", "phoneNumber", "residentialAddress"
        );
        Set<String> actualFields = violations.stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toSet());
        assertEquals(expectedFields, actualFields, "Validation should fail for all required fields");
        log.info("Finished registerVoterWithEmptyDTO test");
    }

    @Test
    void registerVoter_InvalidValues_ShouldFailValidation() {
        log.info("Starting registerVoter_InvalidValues_ShouldFailValidation test");
        var invalidDTO = new VoterRegisterDTO();
        invalidDTO.setFirstName("k");
        invalidDTO.setLastName("LooooooooooooooooooooooooooooooooooooooooooooooooooooongName");
        invalidDTO.setMiddleName("MiddleNameIsWayTooLongForValidation");
        invalidDTO.setSuffixName("TooLongSuffix");
        invalidDTO.setDateOfBirth(LocalDate.of(2003, 5, 1));
        invalidDTO.setGender(VoterRegisterDTO.GenderEnum.MALE);
        invalidDTO.setDmvNumber("12345");
        invalidDTO.setSsnNumber("splashy");
        invalidDTO.setEmail("invalid-email");
        invalidDTO.setPhoneNumber("123456");
        invalidDTO.setFirstVotedYear(1800);

        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(invalidDTO);

        assertThat(violations).extracting(violation -> violation.getPropertyPath().toString() + " - " + violation.getMessage())
                .contains(
                        "firstName - size must be between 2 and 50",
                        "lastName - size must be between 2 and 50",
                        "middleName - size must be between 0 and 20",
                        "suffixName - size must be between 0 and 10",
                        "dmvNumber - DMV number must contain exactly 9 digits",
                        "ssnNumber - SSN number must contain exactly 9 digits",
                        "email - must be a well-formed email address",
                        "phoneNumber - Phone no must contain exactly 11 digits",
                        "firstVotedYear - must be greater than or equal to 1900",
                        "residentialAddress - must not be null"
                );
        log.info("Finished registerVoter_InvalidValues_ShouldFailValidation test");
    }
}