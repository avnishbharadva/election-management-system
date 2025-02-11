package com.ems.services.impls;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidatePageResponse;
import com.ems.entities.Candidate;
import com.ems.entities.CandidateName;
import com.ems.exceptions.CandidateAlreadyExistsException;

import com.ems.entities.Election;

import com.ems.exceptions.CandidateNotFoundException;
import com.ems.exceptions.ElectionNotFoundException;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
@Service
@AllArgsConstructor
public class CandidateServiceImpl implements CandidateService {
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

        if (candidateRepository.findByCandidateSSN(candidateDTO.getCandidateSSN()).isPresent()) {
            throw new CandidateAlreadyExistsException("Candidate with SSN " + candidateDTO.getCandidateSSN() + " already exists.");
        }

        var candidate = candidateMapper.toCandidate(candidateDTO);
        var election = electionRepository.findById(candidateDTO.getElectionId())
                .orElseThrow(() -> new ElectionNotFoundException("Election not found with ID: " + candidateDTO.getElectionId()));
        var party = partyRepository.findById(candidateDTO.getPartyId())
                .orElseThrow(() -> new PartyNotFoundException("Party not found with ID: " + candidateDTO.getPartyId()));

        candidate.setElection(election);
        candidate.setParty(party);

        var residentialAddress = candidateDTO.getResidentialAddress();
        var mailingAddress = residentialAddress.equals(candidateDTO.getMailingAddress())
                ? residentialAddress
                : candidateDTO.getMailingAddress();

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

    @Override
    public List<CandidateByPartyDTO> findByPartyName(String candidatePartyName) {
        List<Candidate> candidates=candidateRepository.findByParty_PartyName(candidatePartyName);

        if(candidates.isEmpty()){
            throw new PartyNotFoundException("Party with given name not found");
        }
        return candidates.stream()
                .map(candidateMapper::toCandidateByPartyDTO)
                .toList();

    }

    @Override

    public List<CandidateDTO> findAll() {
        return candidateRepository.findAll()
                .stream().map(candidateMapper::toCandidateDTO)
                .toList();
    }

    @Override
    public void deleteCandidateByCandidateId(Long candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    public Page<CandidateDTO> getPagedCandidate(int page, int perPage, Sort sort) {
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);

        return candidatePage.map(candidateMapper::toCandidateDTO);
    }

    @Override
    public CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage) {
        // Check if election exists
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found with ID: " + electionId));

        Pageable pageable = PageRequest.of(page, perPage);

        // Fetch paged candidates
        Page<Candidate> candidatePage = candidateRepository.findByElection_electionId(electionId, pageable);

        if (candidatePage.isEmpty()) {
            throw new CandidateNotFoundException("No candidates found for Election ID: " + electionId);
        }

        // Convert entities to DTOs
        Page<CandidateDTO> candidateDTOPage = candidatePage.map(candidateMapper::toCandidateDTO);

        return new CandidatePageResponse(
                candidateDTOPage.getContent(),
                candidateDTOPage.getNumber(),
                candidateDTOPage.getTotalPages(),
                candidateDTOPage.getTotalElements(),
                candidateDTOPage.getSize()
        );
    }

}






