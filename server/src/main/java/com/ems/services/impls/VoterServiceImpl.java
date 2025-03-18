package com.ems.services.impls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import com.ems.dtos.*;
import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.events.VoterUpdateEvent;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.VoterStatusDTO;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.openapitools.model.VoterDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {


    private final KafkaTemplate<String,VoterUpdateEvent> kafkaTemplate;

    private static final String DIRECTORY = "D:/Spring/election-management-system/server/uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/photos";
    private static final String SIGNATURE_DIR = DIRECTORY + "/signature";

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;
    private final NameHistoryRepository nameHistoryRepo;
    private final VoterStatusRepository voterStatusRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Override
    public VoterDTO register(VoterRegisterDTO voterRegisterDTO){
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));
        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);


        String imagePath = null;
        if (voterRegisterDTO.getImage() != null) {
            String imageName = voterRegisterDTO.getSsnNumber() + ".png";
            try {
                imagePath = decodeAndSaveBase64Image(voterRegisterDTO.getImage(), PHOTO_DIR, imageName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String signaturePath = null;
        if (voterRegisterDTO.getSignature() != null) {
            String signName = voterRegisterDTO.getSsnNumber() + "_sign.png";
            try {
                signaturePath = decodeAndSaveBase64Image(voterRegisterDTO.getSignature(), SIGNATURE_DIR, signName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(voterRegisterDTO.getImage()!=null)
            voter.setImage(imagePath);
        if(voterRegisterDTO.getSignature()!=null)
            voter.setSignature(signaturePath);

        var voterStatus = voterStatusRepo.findById(voterRegisterDTO.getStatusId()).orElseThrow(() -> new DataNotFoundException("Voter Status Not Found"));
        voter.setVoterStatus(voterStatus);

        var savedVoter = voterRepo.save(voter);

        var residentialAddress = globalMapper.toAddress(voterRegisterDTO.getResidentialAddress());
        residentialAddress.setAddressType(AddressType.RESIDENTIAL);
        var mailingAddress = globalMapper.toAddress(voterRegisterDTO.getMailingAddress());
        mailingAddress.setAddressType(AddressType.MAILING);

        var addressList = List.of(residentialAddress, mailingAddress);
        addressList.forEach(address -> address.setVoter(savedVoter));

        addressRepo.saveAll(addressList);
        savedVoter.setAddress(addressList);
        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());
        return globalMapper.toVoterDTO(savedVoter);
    }

    @Override
    public Page<VoterDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort) {
        log.info("Searching voters with filters: {}", searchDTO);
        Pageable pageable = PageRequest.of(page, size, getSort(sort));
        return  voterRepo.searchVoters(
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
    public List<VoterStatusDTO> getAllStatus() {
        return globalMapper.toVoterStatusDTOList(voterStatusRepo.findAll());
    }


    @Override
    @Transactional
    public org.openapitools.model.VoterDTO updateVoter(String voterId, org.openapitools.model.VoterDTO voterDTO){
        MDC.put("VoterId", voterId);
        log.info("Update process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);
        var oldAddressList = getOldAddressList(existingVoter);

        var updatedVoter = globalMapper.voterDTOtoVoter(voterDTO, existingVoter);
        log.info("updatedVoter party : {},", updatedVoter.getParty().getPartyId());

        if (voterDTO.getImage() != null) {
            try {
                updatedVoter.setImage(saveBase64Image(voterDTO.getImage(), updatedVoter.getSsnNumber(), PHOTO_DIR));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (voterDTO.getSignature() != null) {
            try {
                updatedVoter.setSignature(saveBase64Image(voterDTO.getSignature(), updatedVoter.getSsnNumber(), SIGNATURE_DIR));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (voterDTO != null) {
            if (voterDTO.getPartyId() != null) {
                log.info("voterDto party : {}", voterDTO.getPartyId());
                var party = partyRepo.findById(voterDTO.getPartyId())
                        .orElseThrow(() -> new DataNotFoundException("Party not found with id: " + voterDTO.getPartyId()));
                updatedVoter.setParty(party);
            }
            updateVoterStatus(updatedVoter, voterDTO);
        }
        voterRepo.save(updatedVoter);

        if (voterDTO != null) {
            updateAddress(voterId, voterDTO.getResidentialAddress(), AddressType.RESIDENTIAL);
            updateAddress(voterId, voterDTO.getMailingAddress(), AddressType.MAILING);
        }

        CompletableFuture<SendResult<String,VoterUpdateEvent>> future=kafkaTemplate.send("update-voter-events-topic",voterId,new VoterUpdateEvent( oldVoter, updatedVoter, oldAddressList, updatedVoter.getAddress()));
        future.whenComplete((result,exception)->{
            if(exception!=null){
                log.error("Failed to send message:"+exception.getMessage());
            }
            else{
                log.info("Message sent successfully:"+result.getRecordMetadata());
            }
        });
        return globalMapper.toVoterDTO(updatedVoter);
    }

    private List<Address> getOldAddressList(Voter existingVoter){
        var oldResidentialAddress = new Address();
        BeanUtils.copyProperties(existingVoter.getAddress().get(0), oldResidentialAddress);
        var oldMailingAddress = new Address();
        BeanUtils.copyProperties(existingVoter
                .getAddress()
                .stream()
                .filter(address -> address.getAddressType().equals(AddressType.MAILING))
                .findFirst()
                .orElseThrow(), oldMailingAddress);
        return List.of(oldResidentialAddress, oldMailingAddress);
    }

    private void updateVoterStatus(Voter updatedVoter, org.openapitools.model.VoterDTO voterDTO) {
        if (voterDTO.getStatusId() == null) return;

        var voterStatus = voterStatusRepo.findById(voterDTO.getStatusId())
                .orElseThrow(() -> new DataNotFoundException("Status not found with id: " + voterDTO.getStatusId()));

        updatedVoter.setVoterStatus(voterStatus);
        log.info("Voter Status Id : {}", voterStatus.getStatusId());
    }


    private void updateAddress(String voterId, org.openapitools.model.AddressDTO addressDTO, AddressType addressType) {
        if (addressDTO == null) return;
        log.info("Voter By Address Type");
        Address address = addressRepo.findByVoter_VoterIdAndAddressType(voterId, addressType)
                .orElseThrow(() -> new DataNotFoundException(addressType + " address not found for voter: " + voterId));
        log.info("Voter By Address Type");
        globalMapper.addressDTOToAddress(addressDTO, address);
        addressRepo.save(address);
    }


    private String decodeAndSaveBase64Image(String base64, String directory, String fileName) throws IOException {
        if (base64 == null || base64.isEmpty()) return null;

        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        Path filePath = Paths.get(directory, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, decodedBytes);
        return filePath.toString();
    }

    private String saveBase64Image(String base64String, String ssnNumber, String directory) throws IOException {
        if (base64String == null || base64String.isBlank()) throw new IllegalArgumentException("Base64 string is empty or null");

        String base64Data = base64String.contains(",") ? base64String.split(",", 2)[1].trim() : base64String.trim();
        base64Data = base64Data.replaceAll("[^A-Za-z0-9+/=]", "");

        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        Path filePath = Paths.get(directory, ssnNumber + ".png");

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, decodedBytes);

        log.info("Image saved at: {}", filePath);
        return filePath.getFileName().toString();
    }

}
