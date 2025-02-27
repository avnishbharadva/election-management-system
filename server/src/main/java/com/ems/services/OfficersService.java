package com.ems.services;

import com.ems.dtos.OfficersRegisterDTO;
import com.ems.dtos.OfficersResponseDTO;
import java.util.List;

public interface OfficersService {
    List<OfficersResponseDTO> getAllRoles();
    OfficersRegisterDTO createRole(OfficersRegisterDTO officersRegisterDTO);
}
