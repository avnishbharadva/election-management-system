package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterDTO;
import com.ems.exceptions.PartyNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;


public interface VoterService {
    VoterDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws PartyNotFoundException, IOException;

    Page<VoterDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort);

//    VoterResponseDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);

    VoterDTO updateVoter(String voterId, VoterDTO voterDTO);
}
