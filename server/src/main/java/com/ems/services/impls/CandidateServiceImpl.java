package com.ems.services.impls;

import com.ems.entities.Candidate;
import com.ems.entities.CandidateAddress;
import com.ems.events.EmailSendEvent;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.FileProcessingException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.CandidateDetailsDTO;
import org.openapitools.model.CandidateUpdateDTO;
import org.openapitools.model.ResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

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
    private static final String CANDIDATE_NOT_FOUND="Candidate not found with ID";
    private static final String EMAIL_ERROR="Skipping email notification: Candidate email is null or empty.";
    private static final String CANDIDATE_IMG_ERROR="Error saving candidate image";
    private static final String CANDIDATE_SIGN_ERROR="Error saving candidate signature";
    private static final String ELECTION_NOT_FOUND_MSG = "Election not found with ID: ";

    @Override
    public ResponseDTO findByCandidateSSN(String candidateSSN) {
        log.info("Fetching candidate details for SSN: {}", candidateSSN);
        org.openapitools.model.CandidateDetailsDTO candidateBySSN= candidateRepository.findByLast4SSN(candidateSSN)
                .map(candidateMapper::toCandidateDetailsDTO)
                .orElseThrow(() -> {
                    log.error("No Candidate found with SSN: {}", candidateSSN);
                    return new DataNotFoundException("No Candidate found with SSN: " + candidateSSN);
                });
        return new ResponseDTO(String.format("Data for Candidate with SSN: %s retrieved successfully", candidateSSN) ,candidateBySSN, LocalDateTime.now().atOffset(ZoneOffset.UTC),true);
    }

    @Override
    @Transactional
    public org.openapitools.model.ResponseDTO saveCandidate(org.openapitools.model.CandidateDTO candidateDTO) {
        if (candidateRepository.findByCandidateSSN(candidateDTO.getCandidateSSN()).isPresent()) {
            throw new DataAlreadyExistException("Candidate with SSN " + candidateDTO.getCandidateSSN() + " already exists.");
        }
        var candidate = candidateMapper.toCandidate(candidateDTO);
        var election = electionRepository.findById(candidateDTO.getElectionId())
                .orElseThrow(() -> new DataNotFoundException(ELECTION_NOT_FOUND_MSG + candidateDTO.getElectionId()));
        var party = partyRepository.findById(candidateDTO.getPartyId())
                .orElseThrow(() -> new DataNotFoundException("Party not found with ID: " + candidateDTO.getPartyId()));

        candidate.setElection(election);
        candidate.setParty(party);
        String imagePath = null;
        if (candidateDTO.getCandidateImage() != null && !candidateDTO.getCandidateImage().isEmpty()) {
            String imageName = candidateDTO.getCandidateSSN() + ".png";
            try {
                imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
                log.info("Candidate image saved at: " + imagePath);
            } catch (IOException e) {
                log.error(CANDIDATE_IMG_ERROR, e);
                throw new FileProcessingException("Failed to save candidate image");
            }
        }

        // Handle Candidate Signature
        String signaturePath = null;
        if (candidateDTO.getCandidateSignature() != null && !candidateDTO.getCandidateSignature().isEmpty()) {
            String signName = candidateDTO.getCandidateSSN() + "_sign.png";
            try {
                signaturePath = decodeAndSaveBase64Image(candidateDTO.getCandidateSignature(), uploadDir, signName);
                log.info("Candidate signature saved at: " + signaturePath);
            } catch (IOException e) {
                log.error(CANDIDATE_SIGN_ERROR, e);
                throw new FileProcessingException("Failed to save candidate signature");
            }

        }

        // Set image and signature paths in candidate entity
        if (imagePath != null) {
            candidate.setCandidateImage(imagePath);
        } else {
            log.warn("Candidate image path is null.");
        }

        if (signaturePath != null) {
            candidate.setCandidateSignature(signaturePath);
        } else {
            log.warn("Candidate signature path is null.");
        }

        // Handle Address Fields
        var residentialAddress = candidate.getResidentialAddress();
        var mailingAddress = residentialAddress.equals(candidate.getMailingAddress())
                ? residentialAddress
                : candidate.getMailingAddress();
        candidate.setResidentialAddress(residentialAddress);
        candidate.setMailingAddress(mailingAddress);

        // Construct full name
        String fullName = candidate.getCandidateName().getFirstName() + " " +
                (candidate.getCandidateName().getMiddleName() != null ? candidate.getCandidateName().getMiddleName() + " " : "") +
                candidate.getCandidateName().getLastName();

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
        log.info("Saving candidate to database...");
        Candidate savedCandidateUnmapped = candidateRepository.save(candidate);
        org.openapitools.model.CandidateDTO savedCandidate=candidateMapper.toCandidateDTO(savedCandidateUnmapped);
        log.info("Candidate saved successfully with ID: " + savedCandidate.getCandidateId());
        return new org.openapitools.model.ResponseDTO("Candidate Saved Successfully",savedCandidate, LocalDateTime.now().atOffset(ZoneOffset.UTC),true);
        }

    private String decodeAndSaveBase64Image(String base64, String directory, String fileName) throws IOException {
        if (base64 == null || base64.isEmpty()) {
            log.warn("Base64 string is null or empty. Skipping image saving.");
            return null; // No image provided
        }

        try {
            // Decode Base64 string
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            // Create directory if it does not exist
            Path dirPath = Paths.get(directory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                log.info("Created directory: {}", dirPath.toAbsolutePath());
            }

            // Define file path and save the image
            Path filePath = dirPath.resolve(fileName);
            Files.write(filePath, decodedBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            log.info("Image saved successfully at: {}", filePath.toAbsolutePath());
            return fileName; // Return absolute file path

        } catch (IllegalArgumentException e) {
            log.error("Invalid Base64 string: {}", e.getMessage());
            throw new IOException("Invalid Base64 string", e);
        } catch (IOException e) {
            log.error("Error writing file: {}", e.getMessage());
            throw new IOException("Error saving image file", e);
        }
    }

    @Override
    public ResponseDTO findById(Long candidateId) {
        Path candidateImagePath = Path.of(uploadDir);
        Path candidateSignaturePath = Path.of(uploadDir);

        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new DataNotFoundException("Candidate not found with ID: " + candidateId));

        var candidateDto = candidateMapper.toCandidateDTO(candidate);

        Path imagePath = candidateImagePath.resolve(candidate.getCandidateImage());
        Path signaturePath = candidateSignaturePath.resolve(candidate.getCandidateSignature());

        log.info("Fetching candidate details for ID: {}", candidateId);
        log.info("Candidate image path: {}", imagePath);
        log.info("Candidate signature path: {}", signaturePath);

        if (!Files.exists(imagePath)) {
            log.error("Candidate image file not found at path: {}", imagePath);
            throw new DataNotFoundException("Candidate image file not found for ID: " + candidateId);
        }

        if (!Files.exists(signaturePath)) {
            log.error("Candidate signature file not found at path: {}", signaturePath);
            throw new DataNotFoundException("Candidate signature file not found for ID: " + candidateId);
        }

        String candidateImageResource = encodeFileToBase64(imagePath);
        String signatureResource = encodeFileToBase64(signaturePath);

        log.info("Successfully encoded candidate image and signature for ID: {}", candidateId);

        candidateDto.setCandidateImage(candidateImageResource);
        candidateDto.setCandidateSignature(signatureResource);
        return new ResponseDTO(String.format("Data for Candidate with ID: %d retrieved successfully", candidateId) ,candidateDto, LocalDateTime.now().atOffset(ZoneOffset.UTC),true) ;
    }


    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                log.info("Encoded file to Base64: {}", encodedString);
                return encodedString;
            } else {
                log.warn("File does not exist at path: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Error encoding file to Base64 at path: {}", filePath, e);
        }
        return null;
    }

    @Override
    @Transactional
    public ResponseDTO update(Long candidateId, CandidateUpdateDTO candidateDTO) {
        log.info("Updating candidate with ID: {}", candidateId);

        // Fetch existing candidate
        Candidate existingCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    log.error("No candidate found with ID: {}", candidateId);
                    return new DataNotFoundException("Candidate not found with ID: " + candidateId);
                });

        log.info("Candidate found: {}", existingCandidate.getCandidateName().getFirstName());

        // Map non-null values from DTO to existing entity
        candidateMapper.updateCandidateFromDTO(candidateDTO, existingCandidate);
        log.info("Candidate details mapped from DTO.");

        // Update Party
        if (candidateDTO.getPartyId() != null && candidateDTO.getPartyId() > 0) {
            existingCandidate.setParty(partyRepository.findById(candidateDTO.getPartyId())
                    .orElseThrow(() -> {
                        log.error("Party not found with ID: {}", candidateDTO.getPartyId());
                        return new RuntimeException("Party not found with ID: " + candidateDTO.getPartyId());
                    }));
            log.info("Updated candidate party to ID: {}", candidateDTO.getPartyId());
        }

        // Update Election
        if (candidateDTO.getElectionId() != null && candidateDTO.getElectionId() > 0) {
            existingCandidate.setElection(electionRepository.findById(candidateDTO.getElectionId())
                    .orElseThrow(() -> {
                        log.error("Election not found with ID: {}", candidateDTO.getElectionId());
                        return new DataNotFoundException(ELECTION_NOT_FOUND_MSG + candidateDTO.getElectionId());
                    }));
            log.info("Updated candidate election to ID: {}", candidateDTO.getElectionId());
        }

        CandidateAddress residentialAddress=candidateMapper.toCandidateAddress(candidateDTO.getResidentialAddress());
        CandidateAddress mailingAddress=candidateMapper.toCandidateAddress(candidateDTO.getMailingAddress());


        if (residentialAddress == null) {
            residentialAddress = mailingAddress;
        } else if (mailingAddress == null) {
            mailingAddress = residentialAddress;
        } else if (residentialAddress.equals(mailingAddress)) {
            mailingAddress = residentialAddress;
        }
        existingCandidate.setResidentialAddress(residentialAddress);
        existingCandidate.setMailingAddress(mailingAddress);
        if (existingCandidate.getMailingAddress() == null) {
            existingCandidate.setMailingAddress(existingCandidate.getResidentialAddress());
        }

        log.info("Updated candidate address. Residential: {}, Mailing: {}",
                existingCandidate.getResidentialAddress(), existingCandidate.getMailingAddress());


        if (candidateDTO.getCandidateImage() != null && !candidateDTO.getCandidateImage().isEmpty()) {
            String imageName = candidateId + ".png";
            try {
                String imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
                existingCandidate.setCandidateImage(imagePath);
                log.info("Updated candidate image at path: {}", imagePath);
            } catch (IOException e) {
                log.error("Error updating candidate image: {}", e.getMessage());
                throw new FileProcessingException("Error updating candidate image");
            }
        }

        // --- Candidate Signature Update ---
        if (candidateDTO.getCandidateSignature() != null && !candidateDTO.getCandidateSignature().isEmpty()) {
            String signName = candidateId + "_sign.png";
            try {
                String signaturePath = decodeAndSaveBase64Image(candidateDTO.getCandidateSignature(), uploadDir, signName);
                existingCandidate.setCandidateSignature(signaturePath);
                log.info("Updated candidate signature at path: {}", signaturePath);
            } catch (IOException e) {
                log.error("Error updating candidate signature: {}", e.getMessage());
                throw new FileProcessingException("Error updating candidate image");
            }
        }


        // Construct full name
        StringBuilder fullName = new StringBuilder(existingCandidate.getCandidateName().getFirstName());
        if (existingCandidate.getCandidateName().getMiddleName() != null) {
            fullName.append(" ").append(existingCandidate.getCandidateName().getMiddleName());
        }
        fullName.append(" ").append(existingCandidate.getCandidateName().getLastName());

        // Fetch Party and Election Details
        String partyName = (existingCandidate.getParty() != null) ? existingCandidate.getParty().getPartyName() : "N/A";
        String electionType = (existingCandidate.getElection() != null) ? existingCandidate.getElection().getElectionType() : "N/A";

        log.info("Candidate full name: {}", fullName);
        log.info("Candidate party: {}", partyName);
        log.info("Candidate election type: {}", electionType);

        // --- Send Email Notification ---
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
        // Save and return updated candidate
        Candidate updatedcandidateUnmapped = candidateRepository.save(existingCandidate);
        org.openapitools.model.CandidateDTO updatedCandidate=candidateMapper.toCandidateDTO(updatedcandidateUnmapped);
        log.info("Candidate updated successfully with ID: {}", updatedCandidate.getCandidateId());
        return new ResponseDTO(String.format("Data for Candidate with ID: %d updated successfully", candidateId),updatedCandidate, LocalDateTime.now().atOffset(ZoneOffset.UTC),true) ;
    }

    @Override
    public ResponseDTO deleteCandidateByCandidateId(Long candidateId) {
        var candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new DataNotFoundException(CANDIDATE_NOT_FOUND +":"+candidateId));

        // Delete Candidate Image
        if (candidate.getCandidateImage() != null) {
            Path uploadDirPath=Path.of(uploadDir);
            Path candidateImagePath=uploadDirPath.resolve(candidate.getCandidateImage());
            File candidateImageFile = new File(String.valueOf(candidateImagePath));

            if (candidateImageFile.exists() && candidateImageFile.delete()) {
                log.info("Deleted candidate image: {}", candidate.getCandidateImage());
            } else {
                log.warn("Failed to delete candidate image: {}", candidate.getCandidateImage());
            }
        }

        if (candidate.getCandidateSignature() != null) {
            Path signaurePath=Path.of(uploadDir);
            Path signaturePath1=signaurePath.resolve(candidate.getCandidateSignature());
            File signatureFile = new File(String.valueOf(signaturePath1));
            if (signatureFile.exists() && signatureFile.delete()) {
                log.info("Deleted candidate signature: {}", candidate.getCandidateSignature());
            } else {
                log.warn("Failed to delete candidate signature: {}", candidate.getCandidateSignature());
            }
        }

        candidateRepository.deleteById(candidateId);
        log.info("Successfully deleted candidate with ID: {}", candidateId);
        return new ResponseDTO("Candidate Deleted Successfully",null, LocalDateTime.now().atOffset(ZoneOffset.UTC),true);
    }


    @Override
    public ResponseDTO getPagedCandidate(int page, int perPage,String sortBy, String sortDir) {
        log.info("Fetching paged candidates. Page: {}, PerPage: {}", page, perPage);
        Sort sort=Sort.by(Sort.Direction.fromString(sortDir),sortBy);
        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);

        log.info("Fetched {} candidates for page: {}", candidatePage.getContent().size(), page);
        Page<CandidateDetailsDTO> candidateDetailsDTOS=candidatePage.map(candidateMapper::toCandidateDetailsDTO);
        return new ResponseDTO(String.format("Successfully retrieved %d candidates for page %d.", candidatePage.getContent().size(), page),candidateDetailsDTOS, LocalDateTime.now().atOffset(ZoneOffset.UTC),true);
    }
}
