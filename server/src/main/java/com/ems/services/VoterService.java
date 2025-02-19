package com.ems.services;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;


public interface VoterService {
    VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException;

    Page<VoterRegisterDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth,
                                        String dmvNumber, String ssnNumber, int page, int size, String[] sort);


    @Transactional
    VoterUpdateDTO updateVoter(String id, VoterUpdateDTO voterUpdateDTO, String modifiedBy);
}
