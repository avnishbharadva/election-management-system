package com.ems.services.impls;

import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.CountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
@Slf4j
public class CountServiceImpl implements CountService {
    private final ElectionRepository electionRepository;
    private final CandidateRepository candidateRepository;
    private final VoterRepository voterRepository;
    private final PartyRepository partyRepository;

    @Override
    public Map<String, Long> getCounts() {
        log.info("Fetching counts for elections, voters, candidates, and parties.");

        long electionCount = electionRepository.count();
        long voterCount = voterRepository.count();
        long candidateCount = candidateRepository.count();
        long partyCount = partyRepository.count();

        log.debug("Election count: {}", electionCount);
        log.debug("Voter count: {}", voterCount);
        log.debug("Candidate count: {}", candidateCount);
        log.debug("Party count: {}", partyCount);

        Map<String, Long> counts = new HashMap<>();
        counts.put("elections", electionCount);
        counts.put("voters", voterCount);
        counts.put("candidates", candidateCount);
        counts.put("parties", partyCount);

        log.info("Counts fetched successfully: {}", counts);

        return counts;
    }
}
