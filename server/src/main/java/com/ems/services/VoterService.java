package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoterService {
    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException;
    List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO);

    VoterRegisterDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);
}
