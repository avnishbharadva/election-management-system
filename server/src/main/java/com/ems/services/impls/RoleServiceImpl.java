package com.ems.services.impls;

import com.ems.entities.Role;
import com.ems.entities.constants.RoleType;
import com.ems.repositories.RoleRepository;
import com.ems.dtos.RoleRegisterDTO;
import com.ems.dtos.RoleResponseDTO;
import com.ems.mappers.GlobalMapper;
import com.ems.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final GlobalMapper globalMapper;

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        return globalMapper.toRoleResponseDTO(roleRepository.findAll());
    }

    @Override
    public RoleRegisterDTO createRole(RoleRegisterDTO roleRegisterDTO) {
        roleRegisterDTO.setPassword(passwordEncoder.encode(roleRegisterDTO.getPassword()));
        return globalMapper.toRoleRegisterDTO(roleRepository.save(globalMapper.toRole(roleRegisterDTO)));
    }

    public String login1(String username, String password, HttpServletRequest request) {
        Role user = roleRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            return "Invalid credentials!";
        }

        RoleType role = user.getRole();
        String districtId = null;

        if (role == RoleType.DISTRICT_OFFICER) {
            districtId = request.getHeader("district_id");
            if (districtId == null) {
                return "District ID is required for district officers!";
            }
        }

        log.info("User '{}' logged in with Role: {} and District ID: {}", username, role, districtId);

//        // Store role and districtId (if applicable) in Security Context
//        SecurityContextHolder.getContext().setAuthentication(
//                new UserAuthentication(username, role.name(), districtId)
//        );

        return "Login successful. Role: " + role.name() + (districtId != null ? ", District ID: " + districtId : "");
    }
}