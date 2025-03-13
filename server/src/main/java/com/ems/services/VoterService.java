package com.ems.services;

import com.ems.dtos.*;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface VoterService {


    @Transactional
    org.openapitools.model.VoterDTO register(org.openapitools.model.VoterRegisterDTO voterRegisterDTO);
    Page<VoterDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);

    List<VoterStatusDTO> getAllStatus();

    VoterDTO updateVoter(String voterId, VoterDTO voterDTO);


//    VoterResponseDTO findBySsnNumber(String ssnNumber);
}
