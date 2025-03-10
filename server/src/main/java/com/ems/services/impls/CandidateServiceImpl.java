package com.ems.services.impls;
import com.ems.dtos.CandidateByPartyDTO;
import com.ems.dtos.CandidateDTO;
import com.ems.dtos.CandidateDetailsDTO;
import com.ems.dtos.CandidatePageResponse;
import com.ems.entities.Candidate;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.repositories.CandidateRepository;
import com.ems.repositories.ElectionRepository;
import com.ems.repositories.PartyRepository;
import com.ems.services.CandidateService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ElectionRepository electionRepository;
    private final PartyRepository partyRepository;
    private final JavaMailSender mailSender;
    private static final String EMAIL_ERROR="Skipping email notification: Candidate email is null or empty.";
    private static final String CANDIDATE_IMG_ERROR="Error saving candidate image";
    private static final String CANDIDATE_SIGN_ERROR="Error saving candidate signature";
   private static final String ELECTION_NOT_FOUND_MSG = "Election not found with ID: ";

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public CandidateDetailsDTO findByCandidateSSN(String candidateSSN) {
        log.info("Fetching candidate details for SSN: {}", candidateSSN);
        return candidateRepository.findByLast4SSN(candidateSSN)
                .map(candidateMapper::toCandidateDetailsDTO)
                .orElseThrow(() -> {
                    log.error("No Candidate found with SSN: {}", candidateSSN);
                    return new DataNotFoundException("No Candidate found with SSN: " + candidateSSN);
                });
    }

    @Override
    @Transactional
    public Candidate saveCandidate(CandidateDTO candidateDTO) throws IOException {
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

        // Handle Candidate Image
        String imagePath = null;
        if (candidateDTO.getCandidateImage() != null && !candidateDTO.getCandidateImage().isEmpty()) {
            String imageName = candidateDTO.getCandidateSSN() + ".png";
            try {
                imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
                log.info("Candidate image saved at: " + imagePath);
            } catch (IOException e) {
                log.error(CANDIDATE_IMG_ERROR, e);
                throw new IOException(CANDIDATE_IMG_ERROR, e);
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
                throw new IOException(CANDIDATE_SIGN_ERROR, e);
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
        var residentialAddress = candidateDTO.getResidentialAddress();
        var mailingAddress = residentialAddress.equals(candidateDTO.getMailingAddress())
                ? residentialAddress
                : candidateDTO.getMailingAddress();
        candidate.setResidentialAddress(residentialAddress);
        candidate.setMailingAddress(mailingAddress);

        // Construct full name
        String fullName = candidate.getCandidateName().getFirstName() + " " +
                (candidate.getCandidateName().getMiddleName() != null ? candidate.getCandidateName().getMiddleName() + " " : "") +
                candidate.getCandidateName().getLastName();

        // Send Email Notification
        if (candidateDTO.getCandidateEmail() != null && !candidateDTO.getCandidateEmail().isEmpty()) {
            sendEmail(candidateDTO.getCandidateEmail(),
                    "Candidate Registration Successful – Welcome to the Election!",
                    "<div style='font-family: Arial, sans-serif; color: #333;'>" +
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
                            "</div>"
            );
        } else {
            log.info(EMAIL_ERROR);
        }

        // Save Candidate
        log.info("Saving candidate to database...");
        Candidate savedCandidate = candidateRepository.save(candidate);
        log.info("Candidate saved successfully with ID: " + savedCandidate.getCandidateId());

        return savedCandidate;
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


    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            ClassPathResource image = new ClassPathResource("static/image.png");// true enables HTML content

            helper.addInline("companyLogo", image);
            mailSender.send(message);
            log.info("Email sent successfully to {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    public CandidateDTO findById(Long candidateId) {
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

        return candidateDto;
    }


    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                log.info("Encoded file to Base64");
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
    public Candidate update(Long candidateId, CandidateDTO candidateDTO) {
        log.info("Updating candidate with ID: {}", candidateId);

        Candidate existingCandidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    log.error("No candidate found with ID: {}", candidateId);
                    return new DataNotFoundException("Candidate not found with ID: " + candidateId);
                });

        log.info("Candidate found: {}", existingCandidate.getCandidateName().getFirstName());

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

        // Handle Address Updates
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
        log.info("Updated candidate address. Residential: {}, Mailing: {}", residentialAddress, mailingAddress);

        // Update Candidate Image
        if (candidateDTO.getCandidateImage() != null && !candidateDTO.getCandidateImage().isEmpty()) {
            String imageName = candidateId + ".png";
            try {
                String imagePath = decodeAndSaveBase64Image(candidateDTO.getCandidateImage(), uploadDir, imageName);
                existingCandidate.setCandidateImage(imagePath);
                log.info("Updated candidate image at path: {}", imagePath);
            } catch (IOException e) {
                log.error("Error updating candidate image: {}", e.getMessage());
                throw new DataNotFoundException(CANDIDATE_IMG_ERROR);
            }
        }

        // Update Candidate Signature
        if (candidateDTO.getCandidateSignature() != null && !candidateDTO.getCandidateSignature().isEmpty()) {
            String signName = candidateId + "_sign.png";
            try {
                String signaturePath = decodeAndSaveBase64Image(candidateDTO.getCandidateSignature(), uploadDir, signName);
                existingCandidate.setCandidateSignature(signaturePath);
                log.info("Updated candidate signature at path: {}", signaturePath);
            } catch (IOException e) {
                log.error("Error updating candidate signature: {}", e.getMessage());
                throw new DataNotFoundException(CANDIDATE_SIGN_ERROR);
            }
        }

        StringBuilder fullName = new StringBuilder(existingCandidate.getCandidateName().getFirstName());
        if (existingCandidate.getCandidateName().getMiddleName() != null) {
            fullName.append(" ").append(existingCandidate.getCandidateName().getMiddleName());
        }
        fullName.append(" ").append(existingCandidate.getCandidateName().getLastName());

        String partyName = (existingCandidate.getParty() != null) ? existingCandidate.getParty().getPartyName() : "N/A";
        String electionType = (existingCandidate.getElection() != null) ? existingCandidate.getElection().getElectionType() : "N/A";

        log.info("Candidate full name: {}", fullName);
        log.info("Candidate party: {}", partyName);
        log.info("Candidate election type: {}", electionType);

        // Send Email Notification
        if (candidateDTO.getCandidateEmail() != null && !candidateDTO.getCandidateEmail().isEmpty()) {
            sendEmail(candidateDTO.getCandidateEmail(),
                    "Candidate Update Successful",
                    "<div>" +
                            "<img src='cid:companyLogo' style='width:150px; height:auto;'/><br>" +
                            "<h3>Dear " + fullName + ",</h3>" +
                            "<p>Your data has been successfully updated!</p>" +
                            "<b>Party:</b> " + partyName + "<br>" +
                            "<b>Election Type:</b> " + electionType + "<br>" +
                            "</div>"
            );
            log.info("Update email sent to candidate: {}", candidateDTO.getCandidateEmail());
        } else {
            log.warn(EMAIL_ERROR);
        }

        Candidate updatedCandidate = candidateRepository.save(existingCandidate);
        log.info("Candidate updated successfully with ID: {}", updatedCandidate.getCandidateId());

        return updatedCandidate;
    }


    @Override
    public List<CandidateByPartyDTO> findByPartyName(String candidatePartyName) {
        log.info("Fetching candidates for party name: {}", candidatePartyName);

        if (candidatePartyName == null || candidatePartyName.isBlank()) {
            log.error("Party name cannot be null or blank.");
            throw new IllegalArgumentException("Party name cannot be null or blank.");
        }

        List<Candidate> candidates = Optional.ofNullable(candidateRepository.findByParty_PartyName(candidatePartyName))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> {
                    log.error("No candidates found for party: {}", candidatePartyName);
                    return new DataNotFoundException("Party with the given name not found: " + candidatePartyName);
                });

        log.info("Found {} candidates for party: {}", candidates.size(), candidatePartyName);
        return candidates.stream()
                .map(candidateMapper::toCandidateByPartyDTO)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public void deleteCandidateByCandidateId(Long candidateId) {
        log.info("Attempting to delete candidate with ID: {}", candidateId);

        if (!candidateRepository.existsById(candidateId)) {
            log.error("Candidate not found with ID: {}", candidateId);
            throw new DataNotFoundException("Candidate not found with ID:" + candidateId);
        }

        candidateRepository.deleteById(candidateId);
        log.info("Successfully deleted candidate with ID: {}", candidateId);
    }

    @Override
    public Page<CandidateDetailsDTO> getPagedCandidate(int page, int perPage, Sort sort) {
        log.info("Fetching paged candidates. Page: {}, PerPage: {}, Sort: {}", page, perPage, sort);

        Pageable pageable = PageRequest.of(page, perPage, sort);
        Page<Candidate> candidatePage = candidateRepository.findAll(pageable);

        log.info("Fetched {} candidates for page: {}", candidatePage.getContent().size(), page);
        return candidatePage.map(candidateMapper::toCandidateDetailsDTO);
    }

    @Override
    public CandidatePageResponse getCandidateByElectionId(Long electionId, int page, int perPage) {
        log.info("Fetching candidates for election ID: {}. Page: {}, PerPage: {}", electionId, page, perPage);

        if (!electionRepository.existsById(electionId)) {
            log.error("No election found with id: {}", electionId);
            throw new DataNotFoundException("Election not found with id:" + electionId);
        }

        Pageable pageable = PageRequest.of(page, perPage);
        Page<Candidate> candidatePage = candidateRepository.findByElection_electionId(electionId, pageable);

        if (candidatePage.isEmpty()) {
            log.warn("No candidates found for Election ID: {}", electionId);
            throw new DataNotFoundException("No candidates found for Election ID: " + electionId);
        }

        log.info("Found {} candidates for Election ID: {}", candidatePage.getTotalElements(), electionId);

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
        log.info("Fetching all candidates information");

        List<Candidate> candidates = candidateRepository.findAll();

        if (candidates.isEmpty()) {
            log.warn("No candidates found in the database");
            throw new DataNotFoundException("No candidates found");
        }

        log.info("Found {} candidates", candidates.size());
        return candidates.stream().map(candidateMapper::toCandidateDetailsDTO).toList();
    }


    @Override
    public Page<CandidateDTO> searchCandidates(CandidateDTO searchCriteria, int page, int perPage, Sort sort) {
        log.info("Searching candidates with criteria: {}, page: {}, perPage: {}, sort: {}", searchCriteria, page, perPage, sort);

        Pageable pageable = PageRequest.of(page, perPage, sort);
        Specification<Candidate> spec = buildSearchSpecification(searchCriteria);

        Page<Candidate> candidatePage = candidateRepository.findAll(spec, pageable);

        if (candidatePage.isEmpty()) {
            log.warn("No candidates found for the given search criteria: {}", searchCriteria);
            throw new DataNotFoundException("No such candidate with this filters");
        }

        log.info("Found {} candidates matching the search criteria", candidatePage.getTotalElements());
        return candidatePage.map(candidateMapper::toCandidateDTO);
    }


    private Specification<Candidate> buildSearchSpecification(CandidateDTO searchCriteria) {
        log.info("Building search specification for candidate with criteria: {}", searchCriteria);

        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (searchCriteria.getCandidateSSN() != null) {
                log.debug("Filtering by Candidate SSN: {}", searchCriteria.getCandidateSSN());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("candidateSSN"), searchCriteria.getCandidateSSN()));
            }

            if (searchCriteria.getCandidateName() != null) {
                log.debug("Filtering by Candidate Name (LIKE): {}", searchCriteria.getCandidateName());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("candidateName"), "%" + searchCriteria.getCandidateName() + "%"));
            }

            if (searchCriteria.getGender() != null) {
                log.debug("Filtering by Gender: {}", searchCriteria.getGender());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("gender"), searchCriteria.getGender()));
            }

            if (searchCriteria.getMaritialStatus() != null) {
                log.debug("Filtering by Marital Status: {}", searchCriteria.getMaritialStatus());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("maritialStatus"), searchCriteria.getMaritialStatus()));
            }

            if (searchCriteria.getStateName() != null) {
                log.debug("Filtering by State Name (LIKE): {}", searchCriteria.getStateName());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("stateName"), "%" + searchCriteria.getStateName() + "%"));
            }

            if (searchCriteria.getCandidateEmail() != null) {
                log.debug("Filtering by Candidate Email (LIKE): {}", searchCriteria.getCandidateEmail());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("candidateEmail"), "%" + searchCriteria.getCandidateEmail() + "%"));
            }

            if (searchCriteria.getPartyId() != null && searchCriteria.getPartyId() != 0) {
                log.debug("Filtering by Party ID: {}", searchCriteria.getPartyId());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("party").get("partyId"), searchCriteria.getPartyId()));
            }

            if (searchCriteria.getElectionId() != null && searchCriteria.getElectionId() != 0) {
                log.debug("Filtering by Election ID: {}", searchCriteria.getElectionId());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("election").get("electionId"), searchCriteria.getElectionId()));
            }

            if (searchCriteria.getNoOfChildren() != 0) {
                log.debug("Filtering by Number of Children: {}", searchCriteria.getNoOfChildren());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("noOfChildren"), searchCriteria.getNoOfChildren()));
            }

            if (searchCriteria.getSpouseName() != null) {
                log.debug("Filtering by Spouse Name (LIKE): {}", searchCriteria.getSpouseName());
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("spouseName"), "%" + searchCriteria.getSpouseName() + "%"));
            }

            log.info("Search specification built successfully");
            return predicate;
        };
    }

}
