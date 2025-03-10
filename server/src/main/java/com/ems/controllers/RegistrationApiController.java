package com.ems.controllers;

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
        OfficersRegisterDTO createdRole = officersService.createRole(officersRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
    }



    @Override
    public ResponseEntity<Map<String, String>> authenticateUser(LoginForm loginForm) {
        return ResponseEntity.ok(registrationService.doAuthenticate(loginForm));
    }

    @Override
    public ResponseEntity<List<OfficersResponseDTO>> getAllOfficers() {
        return ResponseEntity.ok(officersService.getAllRoles());
    }
}
