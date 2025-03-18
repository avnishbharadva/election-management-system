package com.ems.services;

import com.ems.dtos.VoterSearchDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.springframework.data.domain.Page;
import java.util.List;


public interface VoterService {

    VoterDataDTO register(VoterRegisterDTO voterRegisterDTO);
    Page<VoterDataDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);
    List<VoterStatusDataDTO> getAllStatus();
    VoterDataDTO updateVoter(String voterId, VoterUpdateRequest voterDTO);
    VoterDataDTO transferVoterAddress(String voterId, TransferAddress transferAddress);
    VoterDataDTO changeVoterAddress(String voterId, ChangeVoterAddress changeVoterAddress);
}
