package com.ems.controllers;

import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.services.OfficersService;
import com.ems.services.impls.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OfficersApi;
import org.openapitools.model.LoginForm;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class RegistrationApiController implements OfficersApi {

    private final RegistrationServiceImpl registrationService;
    private final OfficersService officersService;

    @Override
    public ResponseEntity<OfficersRegisterDTO> registerOfficer(OfficersRegisterDTO officersRegisterDTO) {
        try {
            OfficersRegisterDTO createdRole = officersService.createRole(officersRegisterDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
        } catch (DataAlreadyExistException e) {
            log.warn("Officer already exists: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            log.error("Error registering officer: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> authenticateUser(LoginForm loginForm) {
        try {
            return ResponseEntity.ok(registrationService.doAuthenticate(loginForm));
        } catch (IllegalCredentials e) {
            log.warn("Invalid login credentials: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            log.error("Unexpected error during authentication: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @Override
    public ResponseEntity<List<OfficersResponseDTO>> getAllRoles() {
        try {
            List<OfficersResponseDTO> roles = officersService.getAllRoles();
            if (roles.isEmpty()) {
                throw new DataNotFoundException("No roles found.");
            }
            return ResponseEntity.ok(roles);
        } catch (DataNotFoundException e) {
            log.warn("No roles found: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            log.error("Error fetching roles: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
