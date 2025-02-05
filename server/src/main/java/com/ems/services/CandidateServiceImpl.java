package com.ems.services;

import com.ems.dtos.CandidateDTO;
import com.ems.entities.BankDetails;
import com.ems.entities.Candidate;
import com.ems.entities.Election;
import com.ems.entities.Party;
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
        Optional<Candidate> candidate = candidateRepository.findByCandidateSSN(candidateSSN);
        if (candidate.isPresent())
        {
            return candidateMapper.toCandidateDTO(candidate.get());
        }
        else {
            throw new CandidateNotFoundException("No Candidate is found with the SSN:"+candidateSSN);
        }

    }

    @Override
    public Candidate save(CandidateDTO candidateDTO) {
        Candidate candidate=candidateMapper.toCandidate(candidateDTO);

        Long electionId=candidateDTO.getElectionId();
        Election election=electionRepository.findById(electionId).get();
        candidate.setElection(election);

        Long partyId=candidateDTO.getPartyId();
        Party party=partyRepository.findById(partyId).get();
        candidate.setParty(party);

        return candidateRepository.save(candidate);


    }

}
