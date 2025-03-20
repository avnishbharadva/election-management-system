package com.ems.services;

<<<<<<< HEAD
import com.ems.dtos.*;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

=======
import com.ems.dtos.VoterSearchDTO;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.TransferAddress;
import org.openapitools.model.ChangeVoterAddress;
import org.springframework.data.domain.Page;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import java.util.List;


public interface VoterService {

<<<<<<< HEAD

    @Transactional
    org.openapitools.model.VoterDTO register(org.openapitools.model.VoterRegisterDTO voterRegisterDTO);
    Page<VoterDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);

    List<VoterStatusDTO> getAllStatus();

    VoterDTO updateVoter(String voterId, VoterDTO voterDTO);


//    VoterResponseDTO findBySsnNumber(String ssnNumber);
=======
    VoterDataDTO register(VoterRegisterDTO voterRegisterDTO);
    Page<VoterDataDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);
    List<VoterStatusDataDTO> getAllStatus();
    VoterDataDTO updateVoter(String voterId, VoterUpdateRequest voterDTO);
    VoterDataDTO transferVoterAddress(String voterId, TransferAddress transferAddress);
    VoterDataDTO changeVoterAddress(String voterId, ChangeVoterAddress changeVoterAddress);
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
