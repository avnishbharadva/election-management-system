package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;

public interface VoterService {
    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException;
    List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO);

    VoterRegisterDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);
}
