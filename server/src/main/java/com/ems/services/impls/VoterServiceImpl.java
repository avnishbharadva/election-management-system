package com.ems.services.impls;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.events.EmailSendEvent;
import org.openapitools.model.*;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import com.ems.exceptions.CustomException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {

    private final KafkaTemplate<String, VoterUpdateEvent> kafkaTemplate;
    private final KafkaTemplate<String, EmailSendEvent> kafkaEmailTemplate;

    private static final String DIRECTORY = "uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/photos";
    private static final String SIGNATURE_DIR = DIRECTORY + "/signature";

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;
    private final VoterStatusRepository voterStatusRepo;
    private final TownRepository townRepo;
    private final CountyRepository countyRepo;

    @Transactional
    @Override
    public VoterDataDTO register(VoterRegisterDTO voterRegisterDTO) {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());
        if (voterRepo.existsByDmvNumber(voterRegisterDTO.getDmvNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with DMV Number : " + voterRegisterDTO.getDmvNumber());
        if (voterRepo.existsByPhoneNumber(voterRegisterDTO.getPhoneNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with Phone Number : " + voterRegisterDTO.getPhoneNumber());
        if (voterRepo.existsByEmail(voterRegisterDTO.getEmail()))
            throw new DataAlreadyExistException("Voter Already Exist with Email : " + voterRegisterDTO.getEmail());

        var party = partyRepo.findByPartyName(voterRegisterDTO.getParty()).orElseThrow(() -> new DataNotFoundException("Party Not Found with name : " + voterRegisterDTO.getParty()));
        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var voterStatus = voterStatusRepo.findByStatusDesc(voterRegisterDTO.getStatus()).orElseThrow(() -> new DataNotFoundException("Voter Status Not Found"));
        voter.setVoterStatus(voterStatus);

        if (voterRegisterDTO.getImage() != null) {
            var imagePath = saveDocument(voterRegisterDTO.getImage(), voterRegisterDTO.getSsnNumber(), PHOTO_DIR);
            voter.setImage(imagePath);
        }

        if (voterRegisterDTO.getSignature() != null) {
            var signaturePath = saveDocument(voterRegisterDTO.getSignature(), voterRegisterDTO.getSsnNumber(), SIGNATURE_DIR);
            voter.setSignature(signaturePath);
        }

        var residentialAddress = globalMapper.toAddress(voterRegisterDTO.getResidentialAddress());
        if (!countyRepo.existsByCountyName(residentialAddress.getCounty()) || !townRepo.existsByTownName(residentialAddress.getTown()))
            throw new DataNotFoundException("County{" + residentialAddress.getCounty() + "} or Town{" + residentialAddress.getTown() + "} does not exists");

        var mailingAddress = globalMapper.toAddress(voterRegisterDTO.getMailingAddress());
        if (!countyRepo.existsByCountyName(mailingAddress.getCounty()) || !townRepo.existsByTownName(mailingAddress.getTown()))
            throw new DataNotFoundException("County{" + mailingAddress.getCounty() + "} or Town{" + mailingAddress.getTown() + "} does not exists");

        var savedVoter = voterRepo.save(voter);

        addressRepo.saveAll(List.of(residentialAddress, mailingAddress));
        savedVoter.setResidentialAddress(residentialAddress);
        savedVoter.setMailingAddress(mailingAddress);

        sendEmail(savedVoter);
        var voterResponse = globalMapper.toVoterDTO(savedVoter);
        Path imagePath = Path.of(PHOTO_DIR + "/" + voterResponse.getImage());
        voterResponse.setImage(encodeFileToBase64(imagePath));
        Path signaturePath = Path.of(SIGNATURE_DIR + "/" + voterResponse.getSignature());
        voterResponse.setSignature(encodeFileToBase64(signaturePath));
        log.info("voter registration completed for : {}", voterResponse.getSsnNumber());
        return voterResponse;
    }

    @Override
    public Page<VoterDataDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort) {
        log.info("Searching voters with filters: {}", searchDTO);
        Pageable pageable = PageRequest.of(page, size, getSort(sort));
        Page<VoterDataDTO> searchedVoters = voterRepo.searchVoters(
                searchDTO.getFirstName(),
                searchDTO.getLastName(),
                searchDTO.getDateOfBirth(),
                searchDTO.getDmvNumber(),
                searchDTO.getSsnNumber(),
                searchDTO.getCity(),
                pageable).map(globalMapper::toVoterDTO);
        searchedVoters.forEach(voterDataDTO -> voterDataDTO.setImage(encodeFileToBase64(Path.of(PHOTO_DIR + "/" + voterDataDTO.getImage()))));
        searchedVoters.forEach(voterDataDTO -> voterDataDTO.setSignature(encodeFileToBase64(Path.of(SIGNATURE_DIR + "/" + voterDataDTO.getSignature()))));
        return searchedVoters;
    }

    private Sort getSort(String[] sort) {
        if ((sort == null || sort.length == 0)) {
            return Sort.unsorted();
        } else {
            if (sort.length > 1)
                return Sort.by(Sort.Direction.fromOptionalString(sort[1]).orElse(Sort.Direction.ASC), sort[0]);
            return Sort.by(Sort.Direction.fromOptionalString("asc").orElse(Sort.Direction.ASC), sort[0]);
        }
    }

    @Override
    public List<VoterStatusDataDTO> getAllStatus() {
        return globalMapper.toVoterStatusDTOList(voterStatusRepo.findAll());
    }

    @Override
    public List<CountyDataDTO> getAllCounties() {
        return globalMapper.toCountyDTOList(countyRepo.findAll());
    }

    @Override
    public List<TownDataDTO> getAllTowns() {
        return globalMapper.toTownDTOList(townRepo.findAll());
    }

    @Override
    @Transactional
    public VoterDataDTO updateVoter(String voterId, VoterUpdateRequest voterUpdateRequest) {
        MDC.put("VoterId", voterId);
        log.info("Update process started for voter {}, data {}", voterId, voterUpdateRequest);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        if (voterUpdateRequest.getSsnNumber() != null && voterRepo.existsBySsnNumber(voterUpdateRequest.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterUpdateRequest.getSsnNumber());
        if (voterUpdateRequest.getDmvNumber() != null && voterRepo.existsByDmvNumber(voterUpdateRequest.getDmvNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with DMV Number : " + voterUpdateRequest.getDmvNumber());
        if (voterUpdateRequest.getPhoneNumber() != null && voterRepo.existsByPhoneNumber(voterUpdateRequest.getPhoneNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with Phone Number : " + voterUpdateRequest.getPhoneNumber());
        if (voterUpdateRequest.getEmail() != null && voterRepo.existsByEmail(voterUpdateRequest.getEmail()))
            throw new DataAlreadyExistException("Voter Already Exist with Email : " + voterUpdateRequest.getEmail());

        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);
        Address oldResidentialAddress = new Address();
        BeanUtils.copyProperties(oldVoter.getResidentialAddress(), oldResidentialAddress);
        Address oldMailingAddress = new Address();
        BeanUtils.copyProperties(oldVoter.getMailingAddress(), oldMailingAddress);

        var updatedVoter = globalMapper.voterDTOtoVoter(voterUpdateRequest, existingVoter);
        log.info("updated voter details : {}", updatedVoter);

        if (voterUpdateRequest.getImage() != null)
            updatedVoter.setImage(saveDocument(voterUpdateRequest.getImage(), updatedVoter.getSsnNumber(), PHOTO_DIR));

        if (voterUpdateRequest.getSignature() != null)
            updatedVoter.setSignature(saveDocument(voterUpdateRequest.getSignature(), updatedVoter.getSsnNumber(), SIGNATURE_DIR));

        if (voterUpdateRequest.getParty() != null) {
            log.info("updated voterDto party : {}", voterUpdateRequest.getParty());
            var party = partyRepo.findByPartyName(voterUpdateRequest.getParty())
                    .orElseThrow(() -> new DataNotFoundException("Party not found with name: " + voterUpdateRequest.getParty()));
            updatedVoter.setParty(party);
        }
        updateVoterStatus(updatedVoter, voterUpdateRequest);

        voterRepo.save(updatedVoter);

        if (voterUpdateRequest.getResidentialAddress() != null)
            updateAddress(voterId, voterUpdateRequest.getResidentialAddress(), updatedVoter.getResidentialAddress().getAddressId());

        if (voterUpdateRequest.getMailingAddress() != null)
            updateAddress(voterId, voterUpdateRequest.getMailingAddress(), updatedVoter.getMailingAddress().getAddressId());

        CompletableFuture<SendResult<String,VoterUpdateEvent>> future=kafkaTemplate.send("update-voter-events-topic",voterId,new VoterUpdateEvent( oldVoter, updatedVoter, List.of(oldResidentialAddress,oldMailingAddress), List.of(updatedVoter.getResidentialAddress(), updatedVoter.getMailingAddress())));
        future.whenComplete((result,exception)->{
            if(exception!=null){
                log.info("Failed to send message: {}",exception.getMessage());
            }
            else{
                log.info("Message sent successfully: {}",result.getRecordMetadata());
            }
        });

        VoterDataDTO voterResponse = globalMapper.toVoterDTO(updatedVoter);
        Path imagePath = Path.of(PHOTO_DIR + "/" + voterResponse.getImage());
        voterResponse.setImage(encodeFileToBase64(imagePath));
        Path signaturePath = Path.of(SIGNATURE_DIR + "/" + voterResponse.getSignature());
        voterResponse.setSignature(encodeFileToBase64(signaturePath));
        log.info("{} voter updated successfully - {}", voterId, voterResponse);
        return voterResponse;
    }

    private void updateVoterStatus(Voter updatedVoter, VoterUpdateRequest voterUpdateRequest) {
        if (voterUpdateRequest.getStatus() == null) return;

        var voterStatus = voterStatusRepo.findByStatusDesc(voterUpdateRequest.getStatus())
                .orElseThrow(() -> new DataNotFoundException("Status not found: " + voterUpdateRequest.getStatus()));

        updatedVoter.setVoterStatus(voterStatus);
        log.info("Updated Voter Status Id : {} of {}", voterStatus.getStatusDesc(), updatedVoter.getSsnNumber());
    }


    private void updateAddress(String voterId, AddressUpdateDTO addressDTO, Long addressId) {
        if (addressDTO == null) return;
        log.info("Voter Address Type : {} for {}", addressDTO, voterId);
        var address = addressRepo.findById(addressId).orElseThrow(() -> new DataNotFoundException("Address not found for voter id : " + voterId));
        globalMapper.addressDTOToAddress(addressDTO, address);
        addressRepo.save(address);
    }

    private String saveDocument(String file, String ssnNumber, String directory) {
        String extension = extractExtension(file);
        if (extension == null) {
            throw new CustomException("Invalid Base64 image format");
        }
        String fileName = ssnNumber + "." + extension;
        String pureBase64 = file.contains(",") ? file.split(",")[1] : file;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(pureBase64);
            Path filePath = Paths.get(directory, fileName);
            log.info("Filepath : {} of voter document : {}", filePath, ssnNumber);
            log.info("get parent for party symbol to create directory : {}", filePath.getParent());
            Files.createDirectories(filePath.getParent());
            deleteExistingFiles(fileName, directory);
            Files.write(filePath, decodedBytes);
        } catch (Exception ex) {
            throw new CustomException("Failed to save party symbol: " + ex.getMessage());
        }
        return fileName;
    }

    private String extractExtension(String base64) {
        if (base64.startsWith("data:image/")) {
            String[] parts = base64.split(";")[0].split("/");
            return parts.length > 1 ? parts[1] : null;
        }
        return null;
    }

    private void deleteExistingFiles(String fileName, String directory) {
        if (fileName == null || fileName.isBlank())
            return;

        try {
            Path dirPath = Paths.get(directory);
            String baseName = fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;

            try (Stream<Path> files = Files.list(dirPath)) {
                files.filter(file -> file.getFileName().toString().startsWith(baseName))
                        .forEach(file -> {
                            try {
                                Files.deleteIfExists(file);
                                log.info("Deleted old voter file: {}", file);
                            } catch (Exception e) {
                                log.warn("Failed to delete old voter file: {}. Reason: {}", file, e.getMessage());
                                throw new CustomException("Failed to delete old voter file : " + e.getMessage());
                            }
                        });
            }
        } catch (Exception e) {
            log.warn("Failed to list files in directory: {}. Reason: {}", directory, e.getMessage());
            throw new CustomException("Failed to list files in directory : " + e.getMessage());
        }
    }

    private String encodeFileToBase64(Path filePath) {
        try {
            if (Files.exists(filePath)) {
                byte[] fileContent = Files.readAllBytes(filePath);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                log.info("{} : Encoded file to Base64", filePath.getFileName());
                return encodedString;
            } else {
                log.info("File does not exist at path: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Error encoding file to Base64 at path: {}", filePath, e);
            throw new CustomException("Error encoding file to Base64 : " + e.getMessage());
        }
        return null;
    }

    @Transactional
    @Override
    public VoterDataDTO changeVoterAddress(String voterId, ChangeVoterAddress changeVoterAddress) {
        MDC.put("VoterId", voterId);
        log.info("Change Voter Address process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id : " + voterId));

        var updatedVoter = globalMapper.changeVoterDTOtoVoter(changeVoterAddress, existingVoter);
        log.info("update voter details : {}", updatedVoter);
        boolean isTownExist = townRepo.existsByTownName(changeVoterAddress.getResidentialAddress().getTown()) && townRepo.existsByTownName(changeVoterAddress.getMailingAddress().getTown());

        if (isTownExist) {
            addressRepo.saveAll(List.of(updatedVoter.getResidentialAddress(), updatedVoter.getMailingAddress()));
        } else {
            throw new DataNotFoundException("Town Not Found With Town Name : " + changeVoterAddress.getResidentialAddress().getTown() + "/" + changeVoterAddress.getMailingAddress().getTown());
        }
        var voterResponse = globalMapper.toVoterDTO(updatedVoter);
        voterResponse.setImage(encodeFileToBase64(Path.of(PHOTO_DIR + "/" + voterResponse.getImage())));
        voterResponse.setSignature(encodeFileToBase64(Path.of(SIGNATURE_DIR + "/" + voterResponse.getSignature())));
        return voterResponse;
    }

    @Override
    public VoterDataDTO transferVoterAddress(String voterId, TransferAddress transferAddress) {
        log.info("Processing request for User ID: {} | Thread Name: {} | Request ID: {}",
                voterId, Thread.currentThread().getName(), MDC.get("requestId"));

        log.info("Voter Transfer process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        var isCountyExists = countyRepo.existsByCountyName(transferAddress.getResidentialAddress().getCounty()) && countyRepo.existsByCountyName(transferAddress.getMailingAddress().getCounty());
        var isTownExists = townRepo.existsByTownName(transferAddress.getResidentialAddress().getTown()) && townRepo.existsByTownName(transferAddress.getMailingAddress().getTown());

        var updatedVoter = globalMapper.voterTransferDtotoVoter(transferAddress, existingVoter);
        log.info("Transfer voter details : {}", updatedVoter);

        if (isCountyExists && isTownExists) {
            addressRepo.saveAll(List.of(updatedVoter.getResidentialAddress(), updatedVoter.getMailingAddress()));
        } else {
            throw new DataNotFoundException("County or Town does not exists");
        }
        var voterResponse = globalMapper.toVoterDTO(updatedVoter);
        voterResponse.setImage(encodeFileToBase64(Path.of(PHOTO_DIR + "/" + voterResponse.getImage())));
        voterResponse.setSignature(encodeFileToBase64(Path.of(SIGNATURE_DIR + "/" + voterResponse.getSignature())));
        return voterResponse;
    }

    private void sendEmail(Voter voter){
        String fullName = voter.getFirstName() + " " + voter.getLastName();

        String mailSubject="Your Voter Registration is Successful!";
        String mailBody="<div style='font-family: Arial, sans-serif; color: #333;'>" +
                "<img src='cid:companyLogo' style='width:150px; height:auto; margin-bottom: 10px;'/><br>" +
                "<h2 style='color: #2c3e50;'>Dear " + fullName + ",</h2>" +
                "<p>We are pleased to inform you that your voter registration has been successfully processed!</p>" +
                "<p>Your unique Voter SSN is: ["+ voter.getSsnNumber() +"]</p>" +
                "<h3 style='color: #2980b9;'>🔹 Registration Details:</h3>" +
                "<ul>" +
                "<li><b>Voter Name:</b> " + fullName + "</li>" +
                "<li><b>Registration Date:</b> " + voter.getCreated_at() + "</li>" +
                "</ul>" +

                "<p>If you have any questions or need further assistance, feel free to contact us.</p>" +
                "<p style='margin-top: 20px;'><b>Best regards,</b><br>" +
                "<b>Election Commission Team</b></p>" +
                "</div>";
        CompletableFuture<SendResult<String, EmailSendEvent>> future=kafkaEmailTemplate.send("email-send-event-topic", String.valueOf(voter.getSsnNumber()),new EmailSendEvent(voter.getEmail(),mailSubject,mailBody));
        future.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Failed to send Kafka message. Voter SSN: {}, Email: {}, Error: {}",
                        voter.getSsnNumber(), voter.getEmail(), exception.getMessage(), exception);
            } else {
                log.info("Kafka message sent successfully. Topic: {}, Partition: {}, Offset: {}, Voter SSN: {}, Email: {}",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        voter.getSsnNumber(),
                        voter.getEmail());
            }
        });
    }
}