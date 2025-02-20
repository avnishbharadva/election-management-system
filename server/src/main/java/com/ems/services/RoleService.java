package com.ems.services;

import com.ems.dtos.RoleRegisterDTO;
import com.ems.dtos.RoleResponseDTO;
import java.util.List;

public interface RoleService {
    List<RoleResponseDTO> getAllRoles();
    RoleRegisterDTO createRole(RoleRegisterDTO roleRegisterDTO);
}
