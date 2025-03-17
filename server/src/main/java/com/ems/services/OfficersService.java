package com.ems.services;

import org.openapitools.model.OfficersRegisterDTO;
import org.openapitools.model.OfficersResponseDTO;

import java.util.List;

public interface OfficersService {
    List<OfficersResponseDTO> getAllRoles();
    org.openapitools.model.OfficersRegisterDTO createRole(OfficersRegisterDTO officersRegisterDTO);
}
