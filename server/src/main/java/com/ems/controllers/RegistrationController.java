package com.ems.controllers;

import com.ems.dtos.RoleRegisterDTO;
import com.ems.dtos.RoleResponseDTO;
import com.ems.jwt.LoginForm;
import com.ems.services.impls.RegistrationServiceImpl;
import com.ems.services.impls.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RoleServiceImpl roleService;
    private final RegistrationServiceImpl registrationService;

    @GetMapping("/getAllRoles")
    public List<RoleResponseDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @PostMapping("/create")
    public ResponseEntity<RoleRegisterDTO> createRole(@RequestBody RoleRegisterDTO roleRegisterDTO) {
        return ResponseEntity.ok(roleService.createRole(roleRegisterDTO));
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        return registrationService.doAuthenticate(loginForm);
    }
}