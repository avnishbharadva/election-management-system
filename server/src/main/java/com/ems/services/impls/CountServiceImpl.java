package com.ems.services.impls;

import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.CountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CountServiceImpl implements CountService {
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;
    private final PartyRepository partyRepository;

    @Override
    public Map<String, Long> getCounts() {
        Map<String ,Long>counts=new HashMap<>();
        counts.put("elections",electionRepository.count());
        counts.put("voters",voterRepository.count());
        counts.put("candidates", candidateRepository.count());
        counts.put("parties", partyRepository.count());
        return counts;
    }
}
