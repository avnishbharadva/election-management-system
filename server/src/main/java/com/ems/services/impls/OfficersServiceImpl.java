package com.ems.services.impls;

import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.IllegalCredentials;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.CountyRepository;
import com.ems.repositories.OfficersRepository;
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

    private final OfficersRepository officersRepository;
    private final PasswordEncoder passwordEncoder;
    private final GlobalMapper globalMapper;
    private final CountyRepository countyRepository;

    @Override
    public List<OfficersResponseDTO> getAllRoles() {
        List<OfficersResponseDTO> roles = globalMapper.toOfficerResponseDTO(officersRepository.findAll());

        if (roles.isEmpty()) {
            log.warn("No roles found in the database.");
            throw new DataNotFoundException("No roles found.");
        }
        log.info("Fetched {} roles from the database.", roles.size());
        return roles;
    }


    @Override
    public OfficersResponseDTO createRole(OfficersRegisterDTO officersRegisterDTO) {
        if (!countyRepository.existsByCountyName(officersRegisterDTO.getCountyName())) {
            log.warn("County '{}' not found", officersRegisterDTO.getCountyName());
            throw new DataNotFoundException("County '" + officersRegisterDTO.getCountyName() + "' does not exist.");
        }
        if (officersRepository.existsByEmailOrCountyNameOrSsnNumber(officersRegisterDTO.getEmail(), officersRegisterDTO.getCountyName(), officersRegisterDTO.getSsnNumber())) {
            log.warn("Officer with given Email, County, or SSN already exists.");
            throw new DataAlreadyExistException("Officer with this Email, SSN, or County already exists.");
        }
        try {
            officersRegisterDTO.setPassword(passwordEncoder.encode(officersRegisterDTO.getPassword()));
            var savedRole = officersRepository.save(globalMapper.toOfficer(officersRegisterDTO));
            log.info("Created new role: {}", savedRole.getEmail());
            return globalMapper.toOfficerResponseDTO(savedRole);
        } catch (Exception e) {
            log.error("Error creating role: {}", e.getMessage());
            throw new IllegalCredentials("Role creation failed");
        }
    }

}
