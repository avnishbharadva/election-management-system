package com.ems.controllers;

import com.ems.dtos.OfficersRegisterDTO;
import com.ems.dtos.OfficersResponseDTO;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.jwt.LoginForm;
import com.ems.services.OfficersService;
import com.ems.services.impls.RegistrationServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final OfficersService officersService;
    private final RegistrationServiceImpl registrationService;

//    @GetMapping("/getAllRoles")
//    public ResponseEntity<List<OfficersResponseDTO>> getAllRoles() {
//        try {
//            List<OfficersResponseDTO> roles = officersService.getAllRoles();
//            return ResponseEntity.ok(roles);
//        } catch (Exception e) {
//            log.error("Error fetching roles", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

//    @PostMapping("/officers/register")
//    public ResponseEntity<OfficersRegisterDTO> registration(@RequestBody OfficersRegisterDTO officersRegisterDTO) {
//        try {
//            OfficersRegisterDTO createdRole = officersService.createRole(officersRegisterDTO);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdRole);
//        } catch (Exception e) {
//            log.error("Error registering officer: {}", e.getMessage());
//            throw  new DataAlreadyExistException("Data Already Exist");
//        }
//    }

//    @PostMapping("/authenticate")
//    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody LoginForm loginForm) {
//        try{
//            return ResponseEntity.ok(registrationService.doAuthenticate(loginForm));
//        }
//        catch (Exception e)
//        {
//            throw new IllegalCredentials("Invalid Credentials");
//        }
//    }

}   