package com.ems.services.impls;

import com.ems.dtos.OfficersRegisterDTO;
import com.ems.dtos.OfficersResponseDTO;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.RoleRepository;
import com.ems.services.OfficersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try {
            List<OfficersResponseDTO> roles = globalMapper.toRoleResponseDTO(roleRepository.findAll());
            log.info("Fetched {} roles from database", roles.size());
            return roles;
        } catch (Exception e) {
            log.error("Error fetching roles: {}", e.getMessage());
            throw new DataNotFoundException("Failed to fetch roles");
        }
    }

    @Override
    public OfficersRegisterDTO createRole(OfficersRegisterDTO officersRegisterDTO) {
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
