package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;

import java.util.List;

public interface VoterService {
    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO);

    List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO);
}
