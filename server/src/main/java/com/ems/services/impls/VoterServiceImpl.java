package com.ems.services.impls;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.entities.Town;
import org.openapitools.model.ChangeVoterAddress;

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
import org.openapitools.model.TransferAddress;
import org.openapitools.model.VoterDataDTO;
import org.openapitools.model.VoterRegisterDTO;
import org.openapitools.model.VoterStatusDataDTO;
import org.slf4j.MDC;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {

//    private final KafkaTemplate<String,VoterUpdateEvent> kafkaTemplate;
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
    private final TownRepository townRepository;
    private final CountyRepository countyRepository;

    @Transactional
    @Override
    public VoterDataDTO register(VoterRegisterDTO voterRegisterDTO){
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());
        if(voterRepo.existsByDmvNumber(voterRegisterDTO.getDmvNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with DMV Number : " + voterRegisterDTO.getDmvNumber());
        if(voterRepo.existsByPhoneNumber(voterRegisterDTO.getPhoneNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with Phone Number : " + voterRegisterDTO.getPhoneNumber());
        if(voterRepo.existsByEmail(voterRegisterDTO.getEmail()))
            throw new DataAlreadyExistException("Voter Already Exist with Email : " + voterRegisterDTO.getEmail());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));
        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);


        String imagePath = null;
        if (voterRegisterDTO.getImage() != null) {

            try {
                imagePath = saveBase64Image(voterRegisterDTO.getImage(), voter.getVoterId(),PHOTO_DIR);
            } catch (IOException | IllegalArgumentException ex) {
                throw new CustomException(ex.getMessage());
            }
        }

        String signaturePath = null;
        if (voterRegisterDTO.getSignature() != null) {
//            String signName = voterRegisterDTO.getSsnNumber() + "_sign.png";
            try {
                signaturePath = saveBase64Image(voterRegisterDTO.getSignature(), voter.getVoterId(),SIGNATURE_DIR);
            } catch (IOException | IllegalArgumentException ex) {
                throw new CustomException(ex.getMessage());
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
//        addressList.forEach(address -> address.setVoter(savedVoter));

        addressRepo.saveAll(addressList);
        savedVoter.setResidentialAddress(residentialAddress);
        savedVoter.setMailingAddress(mailingAddress);
        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());
        return globalMapper.toVoterDTO(savedVoter);
    }

    @Override
    public Page<VoterDataDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort) {
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
    public List<VoterStatusDataDTO> getAllStatus() {
        return globalMapper.toVoterStatusDTOList(voterStatusRepo.findAll());
    }


    @Override
    @Transactional
    public VoterDataDTO updateVoter(String voterId, org.openapitools.model.VoterUpdateRequest voterUpdateRequest){
        MDC.put("VoterId", voterId);
        log.info("Update process started for voter {}", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));


        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);
//        var oldAddressList = getOldAddressList(existingVoter);


        var updatedVoter = globalMapper.voterDTOtoVoter(voterUpdateRequest, existingVoter);
        log.info("update voter details : {}", updatedVoter);
        log.info("updatedVoter party : {},", updatedVoter.getParty().getPartyId());

        if (voterUpdateRequest.getImage() != null) {
            try {
                updatedVoter.setImage(saveBase64Image(voterUpdateRequest.getImage(), updatedVoter.getVoterId(), PHOTO_DIR));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (voterUpdateRequest.getSignature() != null) {
            try {
                updatedVoter.setSignature(saveBase64Image(voterUpdateRequest.getSignature(), updatedVoter.getVoterId(), SIGNATURE_DIR));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        if (voterUpdateRequest != null) {
            if (voterUpdateRequest.getPartyId() != null) {
                log.info("voterDto party : {}", voterUpdateRequest.getPartyId());
                var party = partyRepo.findById(voterUpdateRequest.getPartyId())
                        .orElseThrow(() -> new DataNotFoundException("Party not found with id: " + voterUpdateRequest.getPartyId()));
                updatedVoter.setParty(party);
            }
            updateVoterStatus(updatedVoter, voterUpdateRequest);
        }
        voterRepo.save(updatedVoter);

        if (voterUpdateRequest.getResidentialAddress() != null) {
            updateAddress(voterId, voterUpdateRequest.getResidentialAddress(), updatedVoter.getResidentialAddress().getAddressId());

            if (voterUpdateRequest.getMailingAddress() != null) {
                updateAddress(voterId, voterUpdateRequest.getMailingAddress(), updatedVoter.getMailingAddress().getAddressId());
            }

//        CompletableFuture<SendResult<String,VoterUpdateEvent>> future=kafkaTemplate.send("update-voter-events-topic",voterId,new VoterUpdateEvent( oldVoter, updatedVoter, oldAddressList, updatedVoter.getAddress()));
//        future.whenComplete((result,exception)->{
//            if(exception!=null){
//                log.error("Failed to send message:"+exception.getMessage());
//            }
//            else{
//                log.info("Message sent successfully:"+result.getRecordMetadata());
//            }
//        });

            var voterDTO = globalMapper.toVoterDTO(updatedVoter);
            return voterDTO;
        }

//    private List<Address> getOldAddressList(Voter existingVoter){
//        var oldResidentialAddress = new Address();
//        BeanUtils.copyProperties(existingVoter.getAddress().get(0), oldResidentialAddress);
//        var oldMailingAddress = new Address();
//        BeanUtils.copyProperties(existingVoter
//                .getAddress()
//                .stream()
//                .filter(address -> address.getAddressType().equals(AddressType.MAILING))
//                .findFirst()
//                .orElseThrow(), oldMailingAddress);
//        return List.of(oldResidentialAddress, oldMailingAddress);
//    }

        private void updateVoterStatus (Voter updatedVoter, org.openapitools.model.VoterUpdateRequest voterUpdateRequest)
        {
            if (voterUpdateRequest.getStatusId() == null) return;

            var voterStatus = voterStatusRepo.findById(voterUpdateRequest.getStatusId())
                    .orElseThrow(() -> new DataNotFoundException("Status not found with id: " + voterUpdateRequest.getStatusId()));

            updatedVoter.setVoterStatus(voterStatus);
            log.info("Voter Status Id : {}", voterStatus.getStatusId());
        }

        private void updateAddress (String voterId, org.openapitools.model.AddressDTO addressDTO, Long addressId){
            if (addressDTO == null) return;
            log.info("Voter Update By Address Type : {}", addressDTO);
            var address = addressRepo.findById(addressId).orElseThrow(() -> new DataNotFoundException("Address not found for voter id : " + voterId));
            log.info("Voter Update By Address Type");
            globalMapper.addressDTOToAddress(addressDTO, address);
            addressRepo.save(address);
        }

        private String saveBase64Image (String base64String, String voterId, String directory) throws IOException {
            if (base64String == null || base64String.isBlank())
                throw new CustomException("Base64 string is empty or null");

            String base64Data = base64String.contains(",") ? base64String.split(",", 2)[1].trim() : base64String.trim();
            base64Data = base64Data.replaceAll("[^A-Za-z0-9+/=]", "");

            byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
            Path filePath = Paths.get(directory, voterId + ".png");

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, decodedBytes);

            log.info("Image saved at: {}", filePath);
            return filePath.getFileName().toString();
        }

        @Transactional
        @Override
        public VoterDataDTO changeVoterAddress (String voterId, ChangeVoterAddress changeVoterAddress){
            MDC.put("VoterId", voterId);
            log.info("Update process started for voter {}", voterId);

            var existingVoter = voterRepo.findById(voterId)
                    .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id : " + voterId));

            var updatedVoter = globalMapper.voterDTOtoVoter(changeVoterAddress, existingVoter);
            log.info("update voter details : {}", updatedVoter);
            log.info("updatedVoter party : {},", updatedVoter.getParty().getPartyId());
            boolean isTownExist = townRepository.existsById(changeVoterAddress.getTownId());

            if (isTownExist) {
                var oldVoter = new Voter();
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
            } else {
                throw new DataNotFoundException("Town Not Found With TownID : " + changeVoterAddress.getTownId());
            }
            Town town = townRepository.findById(changeVoterAddress.getTownId()).orElseThrow(() -> new DataNotFoundException("Town Not Found With Voter : " + voterId));
            var voterDTO = globalMapper.toVoterDTO(updatedVoter);
            voterDTO.getResidentialAddress().setTownName(town.getTownName());
            log.info("town name {}", town.getTownName());
            return voterDTO;
        }

        @Override
        public VoterDataDTO transferVoterAddress (String voterId, TransferAddress transferAddress){
            log.info("Processing request for User ID: {} | Thread Name: {} | Request ID: {}",
                    voterId, Thread.currentThread().getName(), MDC.get("requestId"));

            log.info("Voter Transfer process started for voter {}", voterId);

            var existingVoter = voterRepo.findById(voterId)
                    .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id: " + voterId));

            var updatedVoter = globalMapper.voterTransferDtotoVoter(transferAddress, existingVoter);
            log.info("Transfer voter details : {}", updatedVoter);
            boolean istownId = townRepository.existsById(transferAddress.getTownId());
            boolean iscountyId = countyRepository.existsById(transferAddress.getCountyId());

            if (iscountyId) {
                if (istownId) {
                    var oldVoter = new Voter();
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
                } else {
                    throw new DataNotFoundException("Town Not Found For Voter : " + voterId);
                }
            } else {
                throw new DataNotFoundException("County Not Found For Voter : " + voterId);
            }

            voterRepo.save(updatedVoter);
            var voterDTO = globalMapper.toVoterDTO(updatedVoter);
            var townId = transferAddress.getTownId();
            var townObj = townRepository.findById(townId).orElseThrow(() -> new DataNotFoundException(""));
            var countyId = transferAddress.getCountyId();
            var countyObj = countyRepository.findById(countyId).orElseThrow(() -> new DataNotFoundException(""));

            voterDTO.getResidentialAddress().setTownName(townObj.getTownName());
            voterDTO.getResidentialAddress().setCountyName(countyObj.getCountyName());
            voterDTO.getMailingAddress().setTownName(townObj.getTownName());
            voterDTO.getMailingAddress().setCountyName(countyObj.getCountyName());
            return voterDTO;
        }
    }
}