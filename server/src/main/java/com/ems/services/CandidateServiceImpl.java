package com.ems.services;
import com.ems.dtos.CandidateDTO;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import jakarta.validation.Valid;
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
    public Candidate saveCandidate(@Valid CandidateDTO candidateDTO) {
        var candidate = candidateMapper.toCandidate(candidateDTO);

        candidate.setElection(electionRepository.findById(candidateDTO.getElectionId()).get());
        candidate.setParty(partyRepository.findById(candidateDTO.getPartyId()).get());

        var residentialAddress = candidateDTO.getResidentialAddress();
        var mailingAddress = residentialAddress.equals(candidateDTO.getMailingAddress()) ? residentialAddress : candidateDTO.getMailingAddress();

        candidate.setResidentialAddress(residentialAddress);
        candidate.setMailingAddress(mailingAddress);

        return candidateRepository.save(candidate);
    }

    @Override
    public CandidateDTO findById(Long candidateId) {
       return candidateRepository.findById(candidateId)
               .map(candidateMapper::toCandidateDTO)
               .orElseThrow(()->new CandidateNotFoundException("No Such candidate with id"+candidateId));
    }

    @Override
    public Candidate update(Long candidateId, CandidateDTO candidateDTO) {
        var existingCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + candidateId));

        if (candidateDTO.getCandidateName() != null) {
            CandidateName existingName = existingCandidate.getCandidateName();
            CandidateName newName = candidateDTO.getCandidateName();

            if (existingName == null) {
                existingName = new CandidateName();
            }

            Optional.ofNullable(newName.getFirstName()).ifPresent(existingName::setFirstName);
            Optional.ofNullable(newName.getMiddleName()).ifPresent(existingName::setMiddleName);
            Optional.ofNullable(newName.getLastName()).ifPresent(existingName::setLastName);

            existingCandidate.setCandidateName(existingName);
        }

        Optional.ofNullable(candidateDTO.getCandidateEmail()).ifPresent(existingCandidate::setCandidateEmail);
        Optional.ofNullable(candidateDTO.getCandidateSSN()).ifPresent(existingCandidate::setCandidateSSN);
        Optional.ofNullable(candidateDTO.getDateOfBirth()).ifPresent(existingCandidate::setDateOfBirth);
        Optional.ofNullable(candidateDTO.getGender()).ifPresent(existingCandidate::setGender);
        Optional.ofNullable(candidateDTO.getMaritialStatus()).ifPresent(existingCandidate::setMaritialStatus);
        if (candidateDTO.getNoOfChildren() != 0) {
            existingCandidate.setNoOfChildren(candidateDTO.getNoOfChildren());
        }

        Optional.ofNullable(candidateDTO.getSpouseName()).ifPresent(existingCandidate::setSpouseName);
        Optional.ofNullable(candidateDTO.getResidentialAddress()).ifPresent(existingCandidate::setResidentialAddress);
        Optional.ofNullable(candidateDTO.getMailingAddress()).ifPresent(existingCandidate::setMailingAddress);
        Optional.ofNullable(candidateDTO.getStateName()).ifPresent(existingCandidate::setStateName);
        Optional.ofNullable(candidateDTO.getBankDetails()).ifPresent(existingCandidate::setBankDetails);
        if (candidateDTO.getPartyId() != null && candidateDTO.getPartyId() > 0) {
            existingCandidate.setParty(partyRepository.findById(candidateDTO.getPartyId())
                    .orElseThrow(() -> new RuntimeException("Party not found with ID: " + candidateDTO.getPartyId())));
        }
        if (candidateDTO.getElectionId() != null && candidateDTO.getElectionId() > 0) {
            existingCandidate.setElection(electionRepository.findById(candidateDTO.getElectionId())
                    .orElseThrow(() -> new RuntimeException("Election not found with ID: " + candidateDTO.getElectionId())));
        }
        return candidateRepository.save(existingCandidate);
    }
}






