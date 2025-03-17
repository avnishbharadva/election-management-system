package com.ems.services.impls;

import com.ems.dtos.*;
import com.ems.entities.Candidate;
import com.ems.entities.Election;
import com.ems.events.EmailSendEvent;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final KafkaTemplate<String, EmailSendEvent> kafkaTemplate;
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final JavaMailSender mailSender;
    @Value("${file.upload-dir}")
    private String uploadDir;
    private static final String CANDIDATE_IMAGE = "candidateImage";
    private static final String CANDIDATE_SIGNATURE = "candidateSignature";
    private static final String ELECTION_NOT_FOUND_MSG = "Election not found with ID: ";
    @Override
    public CandidateDetailsDTO findByCandidateSSN(String candidateSSN) {
        return candidateRepository.findByLast4SSN(candidateSSN)
                .map(candidateMapper::toCandidateDetailsDTO)
                .orElseThrow(() -> new DataNotFoundException("No Candidate found with SSN: " + candidateSSN));
    }

    @Override
    @Transactional
    public Candidate saveCandidate(CandidateDTO candidateDTO) throws IOException {
        if (candidateRepository.findByCandidateSSN(candidateDTO.getCandidateSSN()).isPresent()) {
            throw new DataNotFoundException("Candidate with SSN " + candidateDTO.getCandidateSSN() + " already exists.");
        }

        var candidate = candidateMapper.toCandidate(candidateDTO);
        var election = electionRepository.findById(candidateDTO.getElectionId())
                .orElseThrow(() -> new DataNotFoundException(ELECTION_NOT_FOUND_MSG + candidateDTO.getElectionId()));
        var party = partyRepository.findById(candidateDTO.getPartyId())
                .orElseThrow(() -> new DataNotFoundException("Party not found with ID: " + candidateDTO.getPartyId()));

        candidate.setElection(election);
        candidate.setParty(party);
        String imagePath = null;

        if (candidateDTO.getCandidateImage() != null) {
            String imageName = candidateDTO.getCandidateSSN() + ".png";
            try {
                imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
            } catch (IOException e) {
                throw new DataNotFoundException("No image is fetched");
            }
        }

        String signaturePath = null;
        if (candidateDTO.getCandidateSignature() != null) {
            String signName = candidateDTO.getCandidateSSN() + "_sign.png";
            try {
                signaturePath = decodeAndSaveBase64Image(candidateDTO.getCandidateSignature(), uploadDir, signName);
            } catch (IOException e) {
                throw new DataNotFoundException("No image is being fetched");
            }
        }

        if (candidateDTO.getCandidateImage() != null)
            candidate.setCandidateImage(imagePath);
        if (candidateDTO.getCandidateSignature() != null)
            candidate.setCandidateSignature(signaturePath);

        var residentialAddress = candidateDTO.getResidentialAddress();
        var mailingAddress = residentialAddress.equals(candidateDTO.getMailingAddress())
                ? residentialAddress
                : candidateDTO.getMailingAddress();

        candidate.setResidentialAddress(residentialAddress);
        candidate.setMailingAddress(mailingAddress);

        String fullName = candidate.getCandidateName().getFirstName() + " " +
                (candidate.getCandidateName().getMiddleName() != null ? candidate.getCandidateName().getMiddleName() + " " : "") +
                candidate.getCandidateName().getLastName();
//            String fullName = candidate.getCandidateName().getFirstName() + " " +
//                    (candidate.getCandidateName().getMiddleName() != null ? candidate.getCandidateName().getMiddleName() + " " : "") +
//                    candidate.getCandidateName().getLastName();
            String mailSubject="Candidate Registration Successful – Welcome to the Election!";
            String mailBody="<div style='font-family: Arial, sans-serif; color: #333;'>" +
                    "<img src='cid:companyLogo' style='width:150px; height:auto; margin-bottom: 10px;'/><br>" +
                    "<h2 style='color: #2c3e50;'>Dear " + fullName + ",</h2>" +
                    "<p>We are pleased to inform you that your registration as a candidate for the upcoming election has been successfully completed.</p>" +
                    "<p>Your participation in this election is a significant step toward making a difference, and we appreciate your commitment.</p>" +
                    "<h3 style='color: #2980b9;'>🔹 Registration Details:</h3>" +
                    "<ul>" +
                    "<li><b>Candidate Name:</b> " + fullName + "</li>" +
                    "<li><b>Party:</b> " + candidate.getParty().getPartyName() + "</li>" +
                    "<li><b>Election Type:</b> " + candidate.getElection().getElectionType() + "</li>" +
                    "</ul>" +

                    "<h3 style='color: #27ae60;'> What’s Next?</h3>" +
                    "<p>As a candidate, you are now officially part of the electoral process. Keep an eye on upcoming announcements and campaign guidelines.</p>" +

                    "<p>If you have any questions or need further assistance, feel free to contact us.</p>" +
                    "<p style='margin-top: 20px;'><b>Best regards,</b><br>" +
                    "<b>Election Commission Team</b></p>" +
                    "</div>";
        CompletableFuture<SendResult<String,EmailSendEvent>> future=kafkaTemplate.send("email-send-event-topic", String.valueOf(candidate.getCandidateId()),new EmailSendEvent(candidateDTO.getCandidateEmail(),mailSubject,mailBody));
        future.whenComplete((result,exception)->{
            if(exception!=null){
                log.error("Failed to send message:"+exception.getMessage());
            }
            else{
                log.info("Message sent successfully:"+result.getRecordMetadata());
            }
        });
            return candidateRepository.save(candidate);
        }

        private String decodeAndSaveBase64Image(String base64, String directory, String fileName) throws IOException {
            if (base64 == null || base64.isEmpty()) return null;  // No image provided

            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            Path filePath = Paths.get(directory, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, decodedBytes);
            return filePath.toString();  // Return file path
        }
    @Override
    public CandidateDTO findById(Long candidateId) {
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
        candidateDto.setCandidateImage(candidateImageResouce);
        candidateDto.setCandidateSignature(signatureResourse);
        return candidateDto;
    }

    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                logger.info("Encoded file to Base64: {}", encodedString);
                return encodedString;
            } else {
                logger.warn("File does not exist at path: {}", filePath);
            }
        } catch (Exception e) {
            logger.error("Error encoding file to Base64 at path: {}", filePath, e);
        }
        return null;
    }

    @Override
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
                    .orElseThrow(() -> new DataNotFoundException(ELECTION_NOT_FOUND_MSG + candidateDTO.getElectionId())));
        }

        var residentialAddress = candidateDTO.getResidentialAddress();
        var mailingAddress = candidateDTO.getMailingAddress();

        if (residentialAddress == null) {
            residentialAddress = mailingAddress;
        } else if (mailingAddress == null) {
            mailingAddress = residentialAddress;
        } else if (residentialAddress.equals(mailingAddress)) {
            mailingAddress = residentialAddress;
        }

        existingCandidate.setResidentialAddress(residentialAddress);
        existingCandidate.setMailingAddress(mailingAddress);

        // Update candidate image if provided
        if (candidateDTO.getCandidateImage() != null && !candidateDTO.getCandidateImage().isEmpty()) {
            String imageName = candidateId + ".png";
            try {
                String imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
                existingCandidate.setCandidateImage(imagePath);
            } catch (IOException e) {
                throw new DataNotFoundException("Error saving candidate image");
            }
        }

        // Update candidate signature if provided
        if (candidateDTO.getCandidateSignature() != null && !candidateDTO.getCandidateSignature().isEmpty()) {
            String signName = candidateId + "_sign.png";
            try {
                String signaturePath = decodeAndSaveBase64Image(candidateDTO.getCandidateSignature(), uploadDir, signName);
                existingCandidate.setCandidateSignature(signaturePath);
            } catch (IOException e) {
                throw new DataNotFoundException("Error saving candidate signature");
            }
        }

        StringBuilder fullName = new StringBuilder(existingCandidate.getCandidateName().getFirstName());
        if (existingCandidate.getCandidateName().getMiddleName() != null) {
            fullName.append(" ").append(existingCandidate.getCandidateName().getMiddleName());
        }
        fullName.append(" ").append(existingCandidate.getCandidateName().getLastName());

        String partyName = (existingCandidate.getParty() != null) ? existingCandidate.getParty().getPartyName() : "N/A";
        String electionType = (existingCandidate.getElection() != null) ? existingCandidate.getElection().getElectionType() : "N/A";
            // Update signature if provided
            if (candidateSignature != null && !candidateSignature.isEmpty()) {
                String signatureFileName = UUID.randomUUID() + "_" + candidateSignature.getOriginalFilename();
                Path signaturePath = candidateSignaturePath.resolve(signatureFileName);
                Files.copy(candidateSignature.getInputStream(), signaturePath, StandardCopyOption.REPLACE_EXISTING);
                existingCandidate.setCandidateSignature(signatureFileName);
            }

            // Update addresses
            var residentialAddress = candidateDTO.getResidentialAddress();
            var mailingAddress = residentialAddress.equals(candidateDTO.getMailingAddress())
                    ? residentialAddress
                    : candidateDTO.getMailingAddress();

            existingCandidate.setResidentialAddress(residentialAddress);
            existingCandidate.setMailingAddress(mailingAddress);


            String fullName = existingCandidate.getCandidateName().getFirstName() + " " +
                    (existingCandidate.getCandidateName().getMiddleName() != null ? existingCandidate.getCandidateName().getMiddleName() + " " : "") +
                    existingCandidate.getCandidateName().getLastName();

            String mailSubject ="Candidate updation Successfully";
            String mailBody="<div>" +
                    "<img src='cid:companyLogo' style='width:150px; height:auto;'/><br>" +  // Replace with your logo URL
                    "<h3>Dear " + fullName + ",</h3>" +
                    "<p>Your data is successfully updated!</p>" +
                    "<b>Party:</b> " + existingCandidate.getParty().getPartyName() + "<br>" +
                    "<b>Election Type:</b> " + existingCandidate.getElection().getElectionType() + "<br>" +
                    "</div>";
            CompletableFuture<SendResult<String,EmailSendEvent>> future=kafkaTemplate.send("email-send-event-topic", String.valueOf(existingCandidate.getCandidateId()),new EmailSendEvent(candidateDTO.getCandidateEmail(),mailSubject,mailBody));
        future.whenComplete((result,exception)->{
            if(exception!=null){
                log.error("Failed to send message:"+exception.getMessage());
            }
            else{
                log.info("Message sent successfully:"+result.getRecordMetadata());
            }
        });
            return candidateRepository.save(existingCandidate);
        }
    @Override
    public List<CandidateByPartyDTO> findByPartyName(String candidatePartyName) {
        if (candidatePartyName == null || candidatePartyName.isBlank()) {
            throw new IllegalArgumentException("Party name cannot be null or blank.");
        }

        List<Candidate> candidates = Optional.ofNullable(candidateRepository.findByParty_PartyName(candidatePartyName))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new DataNotFoundException("Party with the given name not found: " + candidatePartyName));

        return candidates.stream()
                .map(candidateMapper::toCandidateByPartyDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void deleteCandidateByCandidateId(Long candidateId) {
        candidateRepository.deleteById(candidateId);
    }

    @Override
    public Page<CandidateDetailsDTO> getPagedCandidate(int page, int perPage, Sort sort) {
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);
        return candidatePage.map(candidateMapper::toCandidateDetailsDTO);
    }

    @Override
    public CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage) {
        if (!electionRepository.existsById(electionId)) {
            throw new DataNotFoundException("Election not found with id:" + electionId);
        }

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
        if (candidatePage.isEmpty()) {
            throw new DataNotFoundException("No such candidate with this filters");
        }
        return candidatePage.map(candidateMapper::toCandidateDTO);
    }

}
