package com.ems.services.impls;

import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidatePageResponse;
import com.ems.entities.Candidate;
import com.ems.exceptions.CandidateAlreadyExistsException;
import com.ems.entities.CandidateName;
import com.ems.entities.Election;
import com.ems.exceptions.CandidateAlreadyExistsException;
import com.ems.exceptions.CandidateNotFoundException;
import com.ems.exceptions.ElectionNotFoundException;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final ObjectMapper objectMapper;
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public CandidateDTO findByCandidateSSN(String candidateSSN) {
        return candidateRepository.findByCandidateSSN(candidateSSN)
                .map(candidateMapper::toCandidateDTO)
                .orElseThrow(() -> new CandidateNotFoundException("No Candidate found with SSN: " + candidateSSN));
    }

    @Override
    @Transactional
    public Candidate saveCandidate(String candidateData, MultipartFile candidateImage, MultipartFile candidateSignature) throws IOException {
        CandidateDTO candidateDTO=objectMapper.readValue(candidateData, CandidateDTO.class);
        if (candidateRepository.findByCandidateSSN(candidateDTO.getCandidateSSN()).isPresent()) {
            throw new CandidateAlreadyExistsException("Candidate with SSN " + candidateDTO.getCandidateSSN() + " already exists.");
        }
        Path candidateImagePath=Path.of(uploadDir,"candidateImage");
        Path candidateSignaturePath=Path.of(uploadDir,"candidateSignature");
        Files.createDirectories(candidateImagePath);
        Files.createDirectories(candidateSignaturePath);

        var candidate = candidateMapper.toCandidate(candidateDTO);
        var election = electionRepository.findById(candidateDTO.getElectionId())
                .orElseThrow(() -> new ElectionNotFoundException("Election not found with ID: " + candidateDTO.getElectionId()));
        var party = partyRepository.findById(candidateDTO.getPartyId())
                .orElseThrow(() -> new PartyNotFoundException("Party not found with ID: " + candidateDTO.getPartyId()));
        candidate.setElection(election);
        candidate.setParty(party);
        if (candidateImage != null && !candidateImage.isEmpty()) {
            String imageFileName = UUID.randomUUID() + "_" + candidateImage.getOriginalFilename(); // Unique filename
            Path imagePath = candidateImagePath.resolve(imageFileName);
            Files.copy(candidateImage.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
            candidate.setCandidateImage(imageFileName);
        }
        if (candidateSignature != null && !candidateSignature.isEmpty()) {
            String signatureFileName = UUID.randomUUID() + "_" + candidateSignature.getOriginalFilename(); // Unique filename
            Path signaturePath = candidateSignaturePath.resolve(signatureFileName);
            Files.copy(candidateSignature.getInputStream(), signaturePath, StandardCopyOption.REPLACE_EXISTING);
            candidate.setCandidateSignature(signatureFileName);
        }
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
                .orElseThrow(() -> new CandidateNotFoundException("No Such candidate with id: " + candidateId));
    }

    @Override
    @Transactional
    public Candidate update(Long candidateId, CandidateDTO candidateDTO) {
        Candidate existingCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new CandidateNotFoundException("Candidate not found with ID: " + candidateId));

        candidateMapper.updateCandidateFromDTO(candidateDTO, existingCandidate);

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
        List<Candidate> candidates = candidateRepository.findByParty_PartyName(candidatePartyName);

        if (candidates.isEmpty()) {
            throw new PartyNotFoundException("Party with the given name not found.");
        }

        return candidates.stream()
                .map(candidateMapper::toCandidateByPartyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CandidateDTO> findAll() {
        return candidateRepository.findAll()
                .stream()
                .map(candidateMapper::toCandidateDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCandidateByCandidateId(Long candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    @Override
    public Page<CandidateDTO> getPagedCandidate(int page, int perPage, Sort sort) {
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);
        return candidatePage.map(candidateMapper::toCandidateDTO);
    }

    @Override
    public CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new ElectionNotFoundException("Election not found with ID: " + electionId));
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Candidate> candidatePage = candidateRepository.findByElection_electionId(electionId, pageable);

        if (candidatePage.isEmpty()) {
            throw new CandidateNotFoundException("No candidates found for Election ID: " + electionId);
        }

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
