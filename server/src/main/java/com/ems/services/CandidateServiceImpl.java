package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService{
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;

    @Override
    public CandidateDTO findByCandidateSSN(String candidateSSN) {
        return candidateRepository.findByCandidateSSN(candidateSSN)
                .map(candidateMapper::toCandidateDTO)
                .orElseThrow(() -> new CandidateNotFoundException("No Candidate is found with the SSN: " + candidateSSN));
    }


    @Override
    public Candidate save(CandidateDTO candidateDTO) {
        var candidate=candidateMapper.toCandidate(candidateDTO);

        var electionId=candidateDTO.getElectionId();
        var election=electionRepository.findById(electionId).get();
        candidate.setElection(election);

        var partyId=candidateDTO.getPartyId();
        var party=partyRepository.findById(partyId).get();
        candidate.setParty(party);

        return candidateRepository.save(candidate);

    }

    @Override
    public CandidateDTO findById(Long candidateId) {
        Optional<Candidate> foundUser=candidateRepository.findById(candidateId);
        if(foundUser.isPresent()){
             foundUser.get();
        }
        return null;
    }


}
