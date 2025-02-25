package com.ems.services;

import com.ems.dtos.*;
import com.ems.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


public interface VoterService {
    VoterDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws DataNotFoundException, IOException;
    Page<VoterDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort);
    VoterDTO updateVoter(String voterId, VoterDTO voterDTO, MultipartFile profileImg, MultipartFile signImg) throws IOException;

    List<VoterStatusDTO> getAllStatus();

    VoterResponseDTO findBySsnNumber(String ssnNumber);
}
