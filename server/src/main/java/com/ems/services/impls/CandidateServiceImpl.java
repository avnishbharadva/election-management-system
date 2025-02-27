package com.ems.services.impls;

import com.ems.dtos.*;
import com.ems.entities.Candidate;
import com.ems.entities.Election;
import com.ems.exceptions.*;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
    public CandidateDetailsDTO findByCandidateSSN(String candidateSSN) {
        return candidateRepository.findByCandidateSSN(candidateSSN)
                .map(candidateMapper::toCandidateDetailsDTO)
                .orElseThrow(() -> new DataNotFoundException("No Candidate found with SSN: " + candidateSSN));
    }

    @Override
    @Transactional
    @CacheEvict(value = "candidatesCache", allEntries = true)
    public Candidate saveCandidate(CandidateDTO candidateDTO, MultipartFile candidateImage, MultipartFile candidateSignature) throws IOException {
//        CandidateDTO candidateDTO=objectMapper.readValue(candidateData, CandidateDTO.class);
        if (candidateRepository.findByCandidateSSN(candidateDTO.getCandidateSSN()).isPresent()) {
            throw new DataNotFoundException("Candidate with SSN " + candidateDTO.getCandidateSSN() + " already exists.");
        }
        Path candidateImagePath=Path.of(uploadDir,"candidateImage");
        Path candidateSignaturePath=Path.of(uploadDir,"candidateSignature");
        Files.createDirectories(candidateImagePath);
        Files.createDirectories(candidateSignaturePath);

        var candidate = candidateMapper.toCandidate(candidateDTO);
        var election = electionRepository.findById(candidateDTO.getElectionId())
                .orElseThrow(() -> new DataNotFoundException("Election not found with ID: " + candidateDTO.getElectionId()));
        var party = partyRepository.findById(candidateDTO.getPartyId())
                .orElseThrow(() -> new DataNotFoundException("Party not found with ID: " + candidateDTO.getPartyId()));
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
    public CandidateDataDTO findById(Long candidateId) {
        Path candidateImagePath=Path.of(uploadDir,"candidateImage");
        Path candidateSignaturePath=Path.of(uploadDir,"candidateSignature");
        Candidate candidate=candidateRepository.findById(candidateId).get();
        var candidateDto=candidateMapper.toCandidateDTO(candidate);
        Path imagepath=candidateImagePath.resolve(candidate.getCandidateImage());
        Path signaturepath=candidateSignaturePath.resolve(candidate.getCandidateSignature());
        System.out.println(signaturepath+"-------------------------------");
//        Resource candidateImageResouce=imagepath.toFile().exists()?new FileSystemResource(imagepath):null;
//        Resource signatureResourse=signaturepath.toFile().exists()?new FileSystemResource(signaturepath):null;
        String candidateImageResouce=encodeFileToBase64(imagepath);
        String signatureResourse=encodeFileToBase64(signaturepath);
        System.out.println(signatureResourse+"//////////////////");
        return new CandidateDataDTO(candidateDto,candidateImageResouce,signatureResourse);
    }

    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                System.out.println(Base64.getEncoder().encodeToString(fileContent)+"****************");
                return Base64.getEncoder().encodeToString(fileContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    @CacheEvict(value = "candidatesCache", allEntries = true)
    @Transactional
    public Candidate update(Long candidateId, CandidateDTO candidateDTO) {
        Candidate existingCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new DataNotFoundException("Candidate not found with ID: " + candidateId));

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
            throw new DataNotFoundException("Party with the given name not found.");
        }

        return candidates.stream()
                .map(candidateMapper::toCandidateByPartyDTO)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "candidatesCache", allEntries = true)
    public void deleteCandidateByCandidateId(Long candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    @Override
    @Cacheable(value = "candidateCache")
    public Page<CandidateDetailsDTO> getPagedCandidate(int page, int perPage, Sort sort) {
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);
        return candidatePage.map(candidateMapper::toCandidateDetailsDTO);
    }

    @Override
    public CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage) {
        Election election = electionRepository.findById(electionId)
                .orElseThrow(() -> new DataNotFoundException("Election not found with ID: " + electionId));
        Pageable pageable = PageRequest.of(page, perPage);
        Page<Candidate> candidatePage = candidateRepository.findByElection_electionId(electionId, pageable);

        if (candidatePage.isEmpty()) {
            throw new DataNotFoundException("No candidates found for Election ID: " + electionId);
        }

        Page<CandidateDetailsDTO> candidateDTOPage = candidatePage.map(candidateMapper::toCandidateDetailsDTO);
        return new CandidatePageResponse(
                candidateDTOPage.getContent(),
                candidateDTOPage.getNumber(),
                candidateDTOPage.getTotalPages(),
                candidateDTOPage.getTotalElements(),
                candidateDTOPage.getSize()
        );
    }


    @Override
    public List<CandidateDetailsDTO> getCandidateInfo() {
        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.isEmpty()) {
            throw new DataNotFoundException("No candidates found");
        }
        return candidates.stream().map(candidateMapper::toCandidateDetailsDTO).
                toList();
    }

    @Override
    public Page<CandidateDTO> searchCandidates(CandidateDTO searchCriteria, int page, int perPage, Sort sort) {
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Specification<Candidate> spec = buildSearchSpecification(searchCriteria);
        Page<Candidate> candidatePage = candidateRepository.findAll(spec, pageable);
        if(candidatePage.isEmpty()){
            throw new DataNotFoundException("No such candidate with this filters");
        }
        return candidatePage.map(candidateMapper::toCandidateDTO);
    }

    private Specification<Candidate> buildSearchSpecification(CandidateDTO searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (searchCriteria.getCandidateSSN() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("candidateSSN"), searchCriteria.getCandidateSSN()));
            }

            if (searchCriteria.getCandidateName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("candidateName"), "%" + searchCriteria.getCandidateName() + "%"));
            }

            if (searchCriteria.getGender() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), searchCriteria.getGender()));
            }

            if (searchCriteria.getMaritialStatus() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("maritialStatus"), searchCriteria.getMaritialStatus()));
            }

            if (searchCriteria.getStateName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("stateName"), "%" + searchCriteria.getStateName() + "%"));
            }

            if (searchCriteria.getCandidateEmail() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("candidateEmail"), "%" + searchCriteria.getCandidateEmail() + "%"));
            }

            if (searchCriteria.getPartyId()!=null && searchCriteria.getPartyId() != 0 ) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("party").get("partyId"), searchCriteria.getPartyId()));
            }

            if (searchCriteria.getElectionId()!=null && searchCriteria.getElectionId() != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("election").get("electionId"), searchCriteria.getElectionId()));
            }

            if (searchCriteria.getNoOfChildren() != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("noOfChildren"), searchCriteria.getNoOfChildren()));
            }

            if (searchCriteria.getSpouseName() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("spouseName"), "%" + searchCriteria.getSpouseName() + "%"));
            }

            return predicate;
        };
    }


}
