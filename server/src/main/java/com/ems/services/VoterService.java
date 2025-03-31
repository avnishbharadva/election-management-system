package com.ems.services;

import com.ems.dtos.VoterSearchDTO;
import org.openapitools.model.*;
import org.springframework.data.domain.Page;
import java.util.List;


public interface VoterService {

    VoterDataDTO register(VoterRegisterDTO voterRegisterDTO);
    Page<VoterDataDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);
    List<VoterStatusDataDTO> getAllStatus();
    List<CountyDataDTO> getAllCounties();
    List<TownDataDTO> getAllTowns();
    VoterDataDTO updateVoter(String voterId, VoterUpdateRequest voterDTO);
    VoterDataDTO transferVoterAddress(String voterId, TransferAddress transferAddress);
    VoterDataDTO changeVoterAddress(String voterId, ChangeVoterAddress changeVoterAddress);
}
