package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import java.io.IOException;


public interface VoterService {
    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException;
    Page<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO , int page , int size , String[] sort);
    VoterRegisterDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);
}
