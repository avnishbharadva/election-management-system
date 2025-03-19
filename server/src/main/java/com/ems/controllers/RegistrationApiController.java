package com.ems.controllers;

import com.ems.services.OfficersService;
import com.ems.services.impls.RegistrationServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.OfficersApi;
import org.openapitools.model.AuthResponseDTO;
import org.openapitools.model.LoginForm;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class RegistrationApiController implements OfficersApi {

    private final RegistrationServiceImpl registrationService;
    private final OfficersService officersService;

    @Override
    public ResponseEntity<OfficersRegisterDTO> registerOfficer(OfficersRegisterDTO officersRegisterDTO) {
        log.info("Starting officer registration for: {}", officersRegisterDTO.getSsnNumber());
        OfficersRegisterDTO createdRole = officersService.createRole(officersRegisterDTO);
        log.info("Officer registered successfully: {}", createdRole.getSsnNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }

    @Override
    public ResponseEntity<AuthResponseDTO> authenticateUser(LoginForm loginForm) {
        log.info("Authenticating user: {}", loginForm.getEmail());
        AuthResponseDTO authResponseDTO = registrationService.doAuthenticate(loginForm);
        log.info("User authentication successful for: {}", loginForm.getEmail());
        return ResponseEntity.ok(authResponseDTO);
    }

    @Override
    public ResponseEntity<List<OfficersResponseDTO>> getAllOfficers() {
        log.info("Fetching all officers.");
        List<OfficersResponseDTO> officers = officersService.getAllRoles();
        log.info("Successfully fetched {} officers.", officers.size());
        return ResponseEntity.ok(officers);
    }
}
