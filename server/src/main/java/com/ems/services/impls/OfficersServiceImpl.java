package com.ems.services.impls;

import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.RoleRepository;
import com.ems.services.OfficersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OfficersServiceImpl implements OfficersService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final GlobalMapper globalMapper;

    @Override
    public List<OfficersResponseDTO> getAllRoles() {
        List<OfficersResponseDTO> roles = globalMapper.toRoleResponseDTO(roleRepository.findAll());

        if (roles.isEmpty()) {
            log.warn("No roles found in the database.");
            throw new DataNotFoundException("No roles found.");
        }
        log.info("Fetched {} roles from the database.", roles.size());
        return roles;
    }


    @Override
    public OfficersRegisterDTO createRole(OfficersRegisterDTO officersRegisterDTO) {
        if (roleRepository.existsByEmail(officersRegisterDTO.getEmail())) {
            log.warn("Officer with email {} already exists", officersRegisterDTO.getEmail());
            throw new DataAlreadyExistException("Officer already exists");
        }
        try {
            officersRegisterDTO.setPassword(passwordEncoder.encode(officersRegisterDTO.getPassword()));
            var savedRole = roleRepository.save(globalMapper.toRole(officersRegisterDTO));
            log.info("Created new role: {}", savedRole.getEmail());
            return globalMapper.toRoleRegisterDTO(savedRole);
        } catch (Exception e) {
            log.error("Error creating role: {}", e.getMessage());
            throw new IllegalCredentials("Role creation failed");
        }
    }

}
