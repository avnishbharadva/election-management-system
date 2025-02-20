package com.ems.services.impls;

import com.ems.dtos.*;
import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.events.VoterUpdateEvent;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {

    private static final String DIRECTORY = "E:/bright_peers/projects/new/election-management-system/server/uploads";
    private static final String PHOTO_DIR = DIRECTORY + "/photos";
    private static final String SIGNATURE_DIR = DIRECTORY + "/signatures";

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;
    private final NameHistoryRepository nameHistoryRepo;
    private final VoterStatusRepository voterStatusRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public VoterDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws IOException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        if(image!=null)
            voter.setImage(getImage(voterRegisterDTO, image));
        if(signature!=null)
            voter.setSignature(getSignature(voterRegisterDTO, signature));

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

        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());
        return globalMapper.toVoterDTO(savedVoter);
    }

    private String getImage(VoterRegisterDTO voterRegisterDTO, MultipartFile image) throws IOException {
        Files.createDirectories(Paths.get(PHOTO_DIR));

        String imageFileName = null;
        if (!image.isEmpty()) {
            imageFileName = voterRegisterDTO.getSsnNumber() + "_" + image.getOriginalFilename();
            var imagePath = Paths.get(DIRECTORY, "/photos").resolve(imageFileName);
            Files.write(imagePath, image.getBytes());
        }
        return imageFileName;
    }

    private String getSignature(VoterRegisterDTO voterRegisterDTO, MultipartFile signature) throws IOException {
        Files.createDirectories(Paths.get(SIGNATURE_DIR));

        String signatureFileName = null;
        if (!signature.isEmpty()) {
            signatureFileName = voterRegisterDTO.getSsnNumber() + "_" + signature.getOriginalFilename();
            var signaturePath = Paths.get(DIRECTORY, "/signatures").resolve(signatureFileName);
            Files.write(signaturePath, signature.getBytes());
        }
        return signatureFileName;
    }

    @Override
    public
    Page<VoterDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort) {
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber);
        log.info("searching starts for : {}", searchDTO);
        return voterRepo.findBy(Example.of(globalMapper.toVoter(searchDTO), ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), query -> query.sortBy(getSort(sort)).page(PageRequest.of(page, size, getSort(sort)))).map(globalMapper::toVoterDTO);
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
    @Transactional
    public VoterDTO updateVoter(String voterId, VoterDTO voterDTO, MultipartFile profileImg, MultipartFile signImg) throws IOException {
        MDC.put("VoterId", voterId);
        log.info("Update process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);
        var oldAddressList = getOldAddressList(existingVoter);

        var updatedVoter = globalMapper.voterDTOtoVoter(voterDTO, existingVoter);

        log.info("updatedVoter party : {},", updatedVoter.getParty().getPartyId());

        updateProfileImage(updatedVoter, profileImg);
        updateSignatureImage(updatedVoter, signImg);

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

        eventPublisher.publishEvent(new VoterUpdateEvent(this, oldVoter, updatedVoter, oldAddressList, updatedVoter.getAddress()));
        return globalMapper.toVoterDTO(updatedVoter);
    }

    private List<Address> getOldAddressList(Voter existingVoter){
        var oldResidentialAddress = new Address();
        BeanUtils.copyProperties(existingVoter.getAddress().getFirst(), oldResidentialAddress);
        var oldMailingAddress = new Address();
        BeanUtils.copyProperties(existingVoter.getAddress().stream().filter(address -> address.getAddressType().equals(AddressType.MAILING)).findFirst().orElseThrow(), oldMailingAddress);
        return List.of(oldResidentialAddress, oldMailingAddress);
    }

    private void updateVoterStatus(Voter updatedVoter, VoterDTO voterDTO) {
        if (voterDTO.getStatusId() == null) return;

        var voterStatus = voterStatusRepo.findById(voterDTO.getStatusId())
                .orElseThrow(() -> new DataNotFoundException("Status not found with id: " + voterDTO.getStatusId()));

        updatedVoter.setVoterStatus(voterStatus);
        log.info("Voter Status Id : {}", voterStatus.getStatusId());
    }

    private void updateProfileImage(Voter updatedVoter, MultipartFile profileImg) throws IOException {
        if (profileImg == null || profileImg.isEmpty()) return;

        deleteExistingFile(PHOTO_DIR, updatedVoter.getImage());

        String imgName = updatedVoter.getSsnNumber() + "_" + profileImg.getOriginalFilename();
        Path imagePath = Paths.get(PHOTO_DIR, imgName);
        Files.write(imagePath, profileImg.getBytes());

        updatedVoter.setImage(imgName);
    }

    private void updateSignatureImage(Voter updatedVoter, MultipartFile signImg) throws IOException {
        if (signImg == null || signImg.isEmpty()) return;

        deleteExistingFile(SIGNATURE_DIR, updatedVoter.getSignature());

        String signName = updatedVoter.getSsnNumber() + "_" + signImg.getOriginalFilename();
        Path signPath = Paths.get(SIGNATURE_DIR, signName);
        Files.write(signPath, signImg.getBytes());

        updatedVoter.setSignature(signName);
    }

    private void deleteExistingFile(String directory, String fileName) {
        if (fileName == null || fileName.isBlank()) return;

        try {
            Path filePath = Paths.get(directory, fileName);
            if (Files.exists(filePath)) {
                Files.deleteIfExists(filePath);
                log.info("Deleted old file: {}", filePath);
            }
        } catch (IOException e) {
            log.warn("Failed to delete file: {}. Reason: {}", fileName, e.getMessage());
        }
    }

    private void updateAddress(String voterId, AddressDTO addressDTO, AddressType addressType) {
        if (addressDTO == null) return;

        Address address = addressRepo.findByVoter_VoterIdAndAddressType(voterId, addressType)
                .orElseThrow(() -> new DataNotFoundException(addressType + " address not found for voter: " + voterId));

        globalMapper.addressDTOToAddress(addressDTO, address);
        addressRepo.save(address);
    }

    @Override
    public List<VoterStatusDTO> getAllStatus() {
        return globalMapper.toVoterStatusDTOList(voterStatusRepo.findAll());
    }

    @Override
    public VoterResponseDTO findBySsnNumber(String ssnNumber) {
        return voterRepo.findBySsnNumber(ssnNumber).orElseThrow(()->new DataNotFoundException("Voter not fount with ssn number : " + ssnNumber));
    }
}
