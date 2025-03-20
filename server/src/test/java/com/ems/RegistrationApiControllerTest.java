package com.ems;

import com.ems.controllers.RegistrationApiController;
import com.ems.services.OfficersService;
import com.ems.services.impls.RegistrationServiceImpl;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

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
        officersResponseDTO.setSsnNumber("123-45-6789");

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
    void registerWithMissingRequiredFields() {
        OfficersRegisterDTO invalidDTO = new OfficersRegisterDTO();
        when(officersService.createRole(invalidDTO)).thenThrow(new IllegalArgumentException("Missing required fields"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            registrationApiController.registerOfficer(invalidDTO);
        });

        assertEquals("Missing required fields", exception.getMessage());
        verify(officersService, times(1)).createRole(invalidDTO);
    }

    @Test
    void registerWithDuplicateSSNOrEmail() {
        when(officersService.createRole(officersRegisterDTO))
                .thenThrow(new RuntimeException("SSN or Email already exists"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.registerOfficer(officersRegisterDTO);
        });
        assertEquals("SSN or Email already exists", exception.getMessage());
        verify(officersService, times(1)).createRole(officersRegisterDTO);
    }

    @Test
    void registerOfficer_FailureError() {
        when(officersService.createRole(officersRegisterDTO))
                .thenThrow(new RuntimeException("Internal Server Error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.registerOfficer(officersRegisterDTO);
        });
        assertEquals("Internal Server Error", exception.getMessage());
        verify(officersService, times(1)).createRole(officersRegisterDTO);
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
        when(officersService.getAllRoles()).thenReturn(List.of(officersResponseDTO));
        ResponseEntity<List<OfficersResponseDTO>> response = registrationApiController.getAllOfficers();
        assertEquals(OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(officersService, times(1)).getAllRoles();
    }

    @Test
    void getAllOfficers_Failure_NoOfficersFound() {
        when(officersService.getAllRoles()).thenReturn(Collections.emptyList());
        ResponseEntity<List<OfficersResponseDTO>> response = registrationApiController.getAllOfficers();
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(officersService, times(1)).getAllRoles();
    }

    @Test
    void getAllOfficers_Failure_InternalServerError() {
        when(officersService.getAllRoles()).thenThrow(new RuntimeException("Database Error"));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            registrationApiController.getAllOfficers();
        });
        assertEquals("Database Error", exception.getMessage());
        verify(officersService, times(1)).getAllRoles();
    }
}
