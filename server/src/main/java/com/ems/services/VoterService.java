package com.ems.services;

import com.ems.dtos.VoterDTO;
import org.springframework.stereotype.Service;

@Service
public interface VoterService {
    public VoterDTO getVoterBySsn(String ssnNumber);
}
