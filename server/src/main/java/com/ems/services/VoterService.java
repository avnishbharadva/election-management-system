package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.exceptions.PartyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


public interface VoterService {
    VoterResponseDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws PartyNotFoundException, IOException;

    Page<VoterResponseDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort);

//    VoterResponseDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);

    VoterResponseDTO updateVoter(String ssnId, VoterResponseDTO voterUpdateDTO);
}
