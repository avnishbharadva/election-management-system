package com.ems;

import com.ems.controllers.RegistrationApiController;
import com.ems.services.OfficersService;
import com.ems.services.impls.RegistrationServiceImpl;
import jakarta.validation.Validation;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.AuthResponseDTO;
import org.openapitools.model.LoginForm;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@ExtendWith(MockitoExtension.class)
class RegistrationApiControllerTest {

    @Mock
    private RegistrationServiceImpl registrationService;

    @Mock
    private OfficersService officersService;

    @InjectMocks
    private RegistrationApiController registrationApiController;

    private OfficersRegisterDTO officersRegisterDTO;
    private OfficersResponseDTO officersResponseDTO;
    private AuthResponseDTO authResponseDTO;
    private LoginForm loginForm;

    @BeforeEach
    void setUp() {
        officersRegisterDTO = new OfficersRegisterDTO();
        officersRegisterDTO.setSsnNumber("123456789");
        officersRegisterDTO.setEmail("test@ems.com");

        officersResponseDTO = new OfficersResponseDTO();
        officersResponseDTO.setSsnNumber("123456789");

        authResponseDTO = new AuthResponseDTO();
        authResponseDTO.setToken("mocked-jwt-token");

        loginForm = new LoginForm();
        loginForm.setEmail("test@ems.com");
        loginForm.setPassword("password123");
    }

    // TEST CASES FOR registerOfficer()

    @Test
    void registerOfficer_Success() {
        when(officersService.createRole(officersRegisterDTO)).thenReturn(officersResponseDTO);
        ResponseEntity<OfficersResponseDTO> response = registrationApiController.registerOfficer(officersRegisterDTO);

        assertEquals(CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("123456789", response.getBody().getSsnNumber());

        verify(officersService, times(1)).createRole(officersRegisterDTO);
    }

    @Test
    void registerOfficer_failure() {
        log.info("Starting registerOfficer_InvalidValues_ShouldFailValidation test");
        var invalidDTO = new OfficersRegisterDTO();
        invalidDTO.setFirstName("k");
        invalidDTO.setLastName("L".repeat(300));
        invalidDTO.setSsnNumber("splashy");
        invalidDTO.setEmail("invalid-email");
        invalidDTO.setPassword("");
        invalidDTO.setRole("");
        invalidDTO.setCounty("");

        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(invalidDTO);

        assertThat(violations).extracting(violation -> violation.getPropertyPath().toString() + " - " + violation.getMessage())
                .containsExactlyInAnyOrder(
                        "firstName - size must be between 2 and 255",
                        "lastName - size must be between 2 and 255",
                        "ssnNumber - must match \"^\\d{9}$\"",
                        "email - must be a well-formed email address",
                        "password - size must be between 1 and 2147483647",
                        "county - size must be between 1 and 2147483647"
                );
        log.info("Finished registerOfficer_InvalidValues_ShouldFailValidation test");
    }

    // TEST CASES FOR authenticateUser()

    @Test
    void authenticateUser_Success() {
        when(registrationService.doAuthenticate(loginForm)).thenReturn(authResponseDTO);
        ResponseEntity<AuthResponseDTO> response = registrationApiController.authenticateUser(loginForm);

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("mocked-jwt-token", response.getBody().getToken());

        verify(registrationService, times(1)).doAuthenticate(loginForm);
    }

    @Test
    void authenticateUser_InvalidInput_ThrowsBadRequest() {
        log.info("Starting authenticateUser_InvalidInput_ThrowsBadRequest test");
        var invalidLoginForm = new LoginForm();
        invalidLoginForm.setEmail("invalid-email");
        invalidLoginForm.setPassword("");

        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(invalidLoginForm);

        assertThat(violations).extracting(violation -> violation.getPropertyPath().toString() + " - " + violation.getMessage())
                .containsExactlyInAnyOrder(
                        "email - must be a well-formed email address",
                        "password - size must be between 1 and 2147483647"
                );
        log.info("Finished authenticateUser_InvalidInput_ThrowsBadRequest test");
    }

    @Test
    void authenticateUserInvalidCredentials() {
        when(registrationService.doAuthenticate(loginForm)).thenThrow(new RuntimeException("Invalid Credentials"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.authenticateUser(loginForm);
        });
        assertEquals("Invalid Credentials", exception.getMessage());
        verify(registrationService, times(1)).doAuthenticate(loginForm);
    }

    @Test
    void authenticateUserMissingCredentials() {
        LoginForm invalidLogin = new LoginForm();
        when(registrationService.doAuthenticate(invalidLogin)).thenThrow(new IllegalArgumentException("Missing credentials"));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationApiController.authenticateUser(invalidLogin);
        });
        assertEquals("Missing credentials", exception.getMessage());
        verify(registrationService, times(1)).doAuthenticate(invalidLogin);
    }

    @Test
    void authenticateUserNotFound() {
        when(registrationService.doAuthenticate(loginForm)).thenThrow(new RuntimeException("User not found"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.authenticateUser(loginForm);
        });
        assertEquals("User not found", exception.getMessage());
        verify(registrationService, times(1)).doAuthenticate(loginForm);
    }

    @Test
    void authenticateUserInternalServerError() {
        when(registrationService.doAuthenticate(loginForm)).thenThrow(new RuntimeException("Internal Server Error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.authenticateUser(loginForm);
        });
        assertEquals("Internal Server Error", exception.getMessage());
        verify(registrationService, times(1)).doAuthenticate(loginForm);
    }

    // TEST CASES FOR getAllOfficers()

    @Test
    void getAllOfficers_Success() {
        log.info("Starting getAllOfficers_Success test");
        OfficersResponseDTO officersResponseDTO = new OfficersResponseDTO();
        officersResponseDTO.setSsnNumber("123456789");

        when(officersService.getAllRoles()).thenReturn(List.of(officersResponseDTO));

        ResponseEntity<List<OfficersResponseDTO>> response = registrationApiController.getAllOfficers();
        log.info("Response Status: {}", response.getStatusCode());
        log.info("Response Body: {}", response.getBody());

        assertEquals(OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(officersService, times(1)).getAllRoles();
        log.info("Finished getAllOfficers_Success test");
    }

    @Test
    void getAllOfficers_Failure_NoOfficersFound() {
        log.info("Starting getAllOfficers_Failure_NoOfficersFound test");
        when(officersService.getAllRoles()).thenReturn(Collections.emptyList());

        ResponseEntity<List<OfficersResponseDTO>> response = registrationApiController.getAllOfficers();
        log.info("Response Status: {}", response.getStatusCode());
        log.info("Response Body: {}", response.getBody());

        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(officersService, times(1)).getAllRoles();
        log.info("Finished getAllOfficers_Failure_NoOfficersFound test");
    }

    @Test
    void getAllOfficers_Failure_InternalServerError() {
        log.info("Starting getAllOfficers_Failure_InternalServerError test");
        when(officersService.getAllRoles()).thenThrow(new RuntimeException("Database Error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.getAllOfficers();
        });
        log.error("Exception Message: {}", exception.getMessage());

        assertEquals("Database Error", exception.getMessage());
        verify(officersService, times(1)).getAllRoles();
        log.info("Finished getAllOfficers_Failure_InternalServerError test");
    }
}
