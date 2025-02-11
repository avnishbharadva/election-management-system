package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoterService {

    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException;
    Page<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO , int page , int size , String[] sort);
    VoterRegisterDTO updateVoter(String id, @Valid VoterUpdateDTO voterUpdateDTO);
}
