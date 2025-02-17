package com.ems.services.impls;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterStatusDTO;
import com.ems.entities.Address;
import com.ems.entities.NameHistory;
import com.ems.entities.Voter;
import com.ems.entities.constants.AddressType;
import com.ems.exceptions.DataNotFoundException;
import com.ems.exceptions.DataAlreadyExistException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.*;
import com.ems.services.AuditService;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;
    private final NameHistoryRepository nameHistoryRepo;
    private final AuditService auditService;
    private final VoterStatusRepository voterStatusRepo;

    @Override
    @Transactional
    public VoterDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws DataNotFoundException, IOException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new DataAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        Files.createDirectories(Paths.get(uploadDir + "/photos"));
        Files.createDirectories(Paths.get(uploadDir + "/signatures"));

        if (!image.isEmpty()) {
            var imagePath = uploadDir + "/photos/" + voterRegisterDTO.getSsnNumber() + "_" + image.getOriginalFilename();
            image.transferTo(new File(imagePath));
            voter.setImage(imagePath);
        }

        if (!signature.isEmpty()) {
            var signaturePath = uploadDir + "/signatures/" + voterRegisterDTO.getSsnNumber() + "_" + signature.getOriginalFilename();
            signature.transferTo(new File(signaturePath));
            voter.setSignature(signaturePath);
        }

        var voterStatus = voterStatusRepo.findById(voterRegisterDTO.getStatusId()).orElseThrow(()->new DataNotFoundException("Voter Status Not Found"));
        voter.setVoterStatus(voterStatus);

        var savedVoter = voterRepo.save(voter);

        var residentialAddress = globalMapper.toAddress(voterRegisterDTO.getResidentialAddress());
        var mailingAddress = globalMapper.toAddress(voterRegisterDTO.getMailingAddress());

        var addressList = List.of(residentialAddress, mailingAddress);
        addressList.forEach(address -> address.setVoter(savedVoter));
        addressRepo.saveAll(addressList);

        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());

        return globalMapper.toVoterDTO(savedVoter);
    }

    @Override
    public
    Page<VoterDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort) {
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber);
        log.info("searching started for criteria : {}", searchDTO);
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
    public VoterDTO updateVoter(String voterId, VoterDTO voterDTO) {
        log.info("update process start for {} voter", voterId);

        var existingVoter = voterRepo.findById(voterId)
                .orElseThrow(() -> new DataNotFoundException("Voter not found with voter id : " + voterId));

        if(voterDTO.getPartyId()!=null)
            partyRepo.findById(voterDTO.getPartyId()).orElseThrow(() -> new DataNotFoundException("Party not found with id : " + voterDTO.getPartyId()));

        var oldVoter = new Voter();
        BeanUtils.copyProperties(existingVoter, oldVoter);

        var updatedVoter = globalMapper.voterDTOtoVoter(voterDTO, existingVoter);

        auditService.voterAudit(oldVoter, updatedVoter);
        voterRepo.save(updatedVoter);

        if (voterDTO.getResidentialAddress() != null) {
            Address residenceAddress = addressRepo
                    .findByVoter_VoterIdAndAddressType(voterId, AddressType.RESIDENTIAL)
                    .orElseThrow(() -> new DataNotFoundException("Residential address not found for voter : " + voterId));
            residenceAddress.setAddressType(AddressType.RESIDENTIAL);

            var oldAddress = new Address();
            BeanUtils.copyProperties(residenceAddress, oldAddress);

            globalMapper.addressDTOToAddress(voterDTO.getResidentialAddress(), residenceAddress); //update

            auditService.addressAudit(updatedVoter, oldAddress, residenceAddress);
            addressRepo.save(residenceAddress);
        }

        if (voterDTO.getMailingAddress() != null) {
            Address mailingAddress = addressRepo
                    .findByVoter_VoterIdAndAddressType(voterId, AddressType.MAILING)
                    .orElseThrow(() -> new RuntimeException("Mailing Address not found for voter : " + voterId));
            mailingAddress.setAddressType(AddressType.MAILING);

            var oldAddress = new Address();
            BeanUtils.copyProperties(mailingAddress, oldAddress);

            globalMapper.addressDTOToAddress(voterDTO.getMailingAddress(), mailingAddress);

            auditService.addressAudit(updatedVoter, oldAddress, mailingAddress);
            addressRepo.save(mailingAddress);
        }

        addHistory(voterDTO, updatedVoter);
        return globalMapper.toVoterDTO(updatedVoter);
    }

    @Override
    public List<VoterStatusDTO> getAllStatus() {
        return globalMapper.toVoterStatusDTOList(voterStatusRepo.findAll());
    }

    private void addHistory(VoterDTO voterUpdateDTO, Voter voter) {
        if (voterUpdateDTO.getFirstName() != null || voterUpdateDTO.getMiddleName() != null || voterUpdateDTO.getLastName() != null || voterUpdateDTO.getSuffixName() != null) {
            NameHistory nameHistory = new NameHistory();
            BeanUtils.copyProperties(voter, nameHistory);
            log.info("history saved for voter id : {}", voter.getVoterId());
            nameHistoryRepo.save(nameHistory);
        }
    }
}
