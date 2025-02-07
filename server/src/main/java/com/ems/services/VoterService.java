package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.exceptions.PartyNotFoundException;

import java.util.List;

public interface VoterService {

    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException;

    List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO);
}
