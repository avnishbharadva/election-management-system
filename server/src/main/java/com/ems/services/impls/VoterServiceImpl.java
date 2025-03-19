package com.ems.services.impls;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterUpdateRequest;
import org.openapitools.model.AddressDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.openapitools.model.ChangeVoterAddress;
import org.openapitools.model.TransferAddress;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
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

import java.io.IOException;
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

    private static final String DIRECTORY = "uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/photos";
    private static final String SIGNATURE_DIR = DIRECTORY + "/signature";

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;
    private final VoterStatusRepository voterStatusRepo;
    private final TownRepository townRepo;

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

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));
        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var voterStatus = voterStatusRepo.findById(voterRegisterDTO.getStatusId()).orElseThrow(() -> new DataNotFoundException("Voter Status Not Found"));
        voter.setVoterStatus(voterStatus);

        if (voterRegisterDTO.getImage() != null) {
            var imagePath = saveDocument(voterRegisterDTO.getImage(), voterRegisterDTO.getSsnNumber(), PHOTO_DIR);
            voter.setImage(imagePath);
        }

        if (voterRegisterDTO.getSignature() != null) {
            var signaturePath = saveDocument(voterRegisterDTO.getSignature(), voterRegisterDTO.getSsnNumber(), SIGNATURE_DIR);
            voter.setSignature(signaturePath);
        }

        var savedVoter = voterRepo.save(voter);

        var residentialAddress = globalMapper.toAddress(voterRegisterDTO.getResidentialAddress());
        residentialAddress.setAddressType(AddressType.RESIDENTIAL);
        var mailingAddress = globalMapper.toAddress(voterRegisterDTO.getMailingAddress());
        mailingAddress.setAddressType(AddressType.MAILING);

        addressRepo.saveAll(List.of(residentialAddress, mailingAddress));
        savedVoter.setResidentialAddress(residentialAddress);
        savedVoter.setMailingAddress(mailingAddress);

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
        return voterRepo.searchVoters(
                searchDTO.getFirstName(),
                searchDTO.getLastName(),
                searchDTO.getDateOfBirth(),
                searchDTO.getDmvNumber(),
                searchDTO.getSsnNumber(),
                searchDTO.getCity(),
                pageable).map(globalMapper::toVoterDTO);
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
    @Transactional
    public VoterDataDTO updateVoter(String voterId, VoterUpdateRequest voterUpdateRequest) {
        MDC.put("VoterId", voterId);
        log.info("Update process started for voter {}, data {}", voterId, voterUpdateRequest);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);
        Address oldResidentialAddress = new Address();
        BeanUtils.copyProperties(oldVoter.getResidentialAddress(), oldResidentialAddress);
        Address oldMailingAddress = new Address();
        BeanUtils.copyProperties(oldVoter.getMailingAddress(), oldMailingAddress);

        var updatedVoter = globalMapper.voterDTOtoVoter(voterUpdateRequest, existingVoter);
        log.info("update voter details : {}", updatedVoter);

        if (voterUpdateRequest.getImage() != null)
            updatedVoter.setImage(saveDocument(voterUpdateRequest.getImage(), updatedVoter.getSsnNumber(), PHOTO_DIR));

        if (voterUpdateRequest.getSignature() != null)
            updatedVoter.setSignature(saveDocument(voterUpdateRequest.getSignature(), updatedVoter.getSsnNumber(), SIGNATURE_DIR));

        if (voterUpdateRequest.getPartyId() != null) {
            log.info("updated voterDto party : {}", voterUpdateRequest.getPartyId());
            var party = partyRepo.findById(voterUpdateRequest.getPartyId())
                    .orElseThrow(() -> new DataNotFoundException("Party not found with id: " + voterUpdateRequest.getPartyId()));
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
        log.info("{} voter updated successfully - {}",voterId,voterResponse);
        return voterResponse;
    }

    private void updateVoterStatus(Voter updatedVoter, VoterUpdateRequest voterUpdateRequest) {
        if (voterUpdateRequest.getStatusId() == null) return;

        var voterStatus = voterStatusRepo.findById(voterUpdateRequest.getStatusId())
                .orElseThrow(() -> new DataNotFoundException("Status not found with id: " + voterUpdateRequest.getStatusId()));

        updatedVoter.setVoterStatus(voterStatus);
        log.info("Updated Voter Status Id : {} of {}", voterStatus.getStatusId(), updatedVoter.getSsnNumber());
    }


    private void updateAddress(String voterId, AddressDTO addressDTO, Long addressId) {
        if (addressDTO == null) return;
        log.info("Voter Address Type : {} for {}", addressDTO, voterId);
        var address = addressRepo.findById(addressId).orElseThrow(() -> new DataNotFoundException("Address not found for voter id : " + voterId));
        globalMapper.addressDTOToAddress(addressDTO, address);
        addressRepo.save(address);
    }

    private String saveDocument(String file, String ssnNumber, String directory){
        String extension = extractExtension(file);
        if (extension == null) {
            throw new CustomException("Invalid Base64 image format");
        }
        String fileName = ssnNumber + "." + extension;
        String pureBase64 = file.contains(",") ? file.split(",")[1] : file;
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(pureBase64);
            Path filePath = Paths.get(directory, fileName);
            log.info("Filepath : {} of voter document : {}",filePath,ssnNumber);
            log.info("get parent for party symbol to create directory : {}", filePath.getParent());
            Files.createDirectories(filePath.getParent());
            deleteExistingFiles(fileName, directory);
            Files.write(filePath, decodedBytes);
        } catch (IOException | IllegalArgumentException ex) {
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
                            } catch (IOException e) {
                                log.warn("Failed to delete old voter file: {}. Reason: {}", file, e.getMessage());
                            }
                        });
            }
        } catch (IOException e) {
            log.warn("Failed to list files in directory: {}. Reason: {}", directory, e.getMessage());
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
        }
        return null;
    }

    @Transactional
    @Override
    public VoterDataDTO changeVoterAddress(String voterId, ChangeVoterAddress changeVoterAddress){
        MDC.put("VoterId", voterId);
        log.info("Change Voter Address process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id : " + voterId));

        var updatedVoter = globalMapper.changeVoterDTOtoVoter(changeVoterAddress, existingVoter);
        log.info("update voter details : {}", updatedVoter);
        boolean isTownExist = townRepo.existsByTownName(changeVoterAddress.getTown());

        if(isTownExist) {
            Address address = new Address();
            if (existingVoter.getResidentialAddress().getAddressType().toString().equals(changeVoterAddress.getAddressType().toString())) {
                var oldAddress = addressRepo.findById(existingVoter.getResidentialAddress().getAddressId()).orElseThrow(() -> new DataNotFoundException("Address Not Found For VoterId : " + voterId));
                address = globalMapper.changeAddressDTOToAddress(changeVoterAddress, oldAddress);
            }
            if (existingVoter.getMailingAddress().getAddressType().toString().equals(changeVoterAddress.getAddressType().toString())) {
                var oldAddress = addressRepo.findById(existingVoter.getMailingAddress().getAddressId()).orElseThrow(() -> new DataNotFoundException("Address Not Found For VoterId : " + voterId));
                address = globalMapper.changeAddressDTOToAddress(changeVoterAddress, oldAddress);
            }
            addressRepo.save(address);
        }
        else
        {
            throw new DataNotFoundException("Town Not Found With TownID : " + changeVoterAddress.getTown());
        }
        return globalMapper.toVoterDTO(updatedVoter);
    }

    @Override
    public VoterDataDTO transferVoterAddress(String voterId, TransferAddress transferAddress){
        log.info("Processing request for User ID: {} | Thread Name: {} | Request ID: {}",
                voterId, Thread.currentThread().getName(), MDC.get("requestId"));

        log.info("Voter Transfer process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        var updatedVoter = globalMapper.voterTransferDtotoVoter(transferAddress, existingVoter);
        log.info("Transfer voter details : {}", updatedVoter);
        Address address = new Address();
        if (existingVoter.getResidentialAddress().getAddressType().toString().equals(transferAddress.getAddressType().toString())) {
            var oldAddress = addressRepo.findById(existingVoter.getResidentialAddress().getAddressId()).orElseThrow(() -> new DataNotFoundException("AddressNot Found For Voter : " + voterId));
            address = globalMapper.transferVoterAddressToAddress(transferAddress, oldAddress);
        }
        if (existingVoter.getMailingAddress().getAddressType().toString().equals(transferAddress.getAddressType().toString())) {
            var oldAddress = addressRepo.findById(existingVoter.getMailingAddress().getAddressId()).orElseThrow(() -> new DataNotFoundException("AddressNot Found For Voter : " + voterId));
            address = globalMapper.transferVoterAddressToAddress(transferAddress, oldAddress);
        }
        addressRepo.save(address);

        voterRepo.save(updatedVoter);
        return globalMapper.toVoterDTO(updatedVoter);
    }

}