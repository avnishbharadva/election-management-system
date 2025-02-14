package com.ems.services.impls;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.NameHistory;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.exceptions.VoterAlreadyExistException;
import com.ems.exceptions.VoterNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.NameHistoryRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
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

    @Override
    @Transactional
    public VoterResponseDTO register(VoterRegisterDTO voterRegisterDTO, MultipartFile image, MultipartFile signature) throws PartyNotFoundException, IOException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        if (voterRepo.existsBySsnNumber(voterRegisterDTO.getSsnNumber()))
            throw new VoterAlreadyExistException("Voter Already Exist with SSN Number : " + voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var addressDTOList = voterRegisterDTO.getAddress();
        var addressList = globalMapper.toAddressList(addressDTOList);

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

        var savedVoter = voterRepo.save(voter);
        addressList.forEach(address -> {
            address.setVoter(savedVoter);
            if (address.getCounty() == null)
                address.setCounty(addressList.getFirst().getCounty());
        });
        addressRepo.saveAll(addressList);
        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());

        return globalMapper.toVoterResponseDTO(savedVoter);
    }

    @Override
    public
    Page<VoterResponseDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort) {
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber);
        log.info("searching started for criteria : {}", searchDTO);
        return voterRepo.findBy(Example.of(globalMapper.toVoter(searchDTO), ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)), query -> query.sortBy(getSort(sort)).page(PageRequest.of(page, size, getSort(sort)))).map(globalMapper::toVoterResponseDTO);
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
    public VoterResponseDTO updateVoter(String ssnId, VoterResponseDTO voterResponseDTO) {
        log.info("update process start for {} voter", ssnId);

        var existingVoter = voterRepo.findBySsnNumber(ssnId).orElseThrow(() -> new VoterNotFoundException("Voter not found with SSN : " + ssnId));

        if (voterResponseDTO.getPartyId() != null)
            partyRepo.findById(voterResponseDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party not found with ssnId : " + voterResponseDTO.getPartyId()));

        var addressDTOList = voterResponseDTO.getAddress();
        var updatedAddress = globalMapper.toAddressList(addressDTOList);

//        addressDTOList.stream().map(addressDTO -> globalMapper.updateAddressToAddress(addressDTO,))

        var updatedVoter = globalMapper.updateDTOtoVoter(voterResponseDTO, existingVoter);

        addHistory(voterResponseDTO, existingVoter);
        auditService.voterAudit(existingVoter, updatedVoter);

        voterRepo.save(existingVoter);
        log.info("updated voter details of : {}", ssnId);
        return globalMapper.toVoterResponseDTO(existingVoter);
    }

    private void addHistory(VoterResponseDTO voterUpdateDTO, Voter voter) {
        if (voterUpdateDTO.getFirstName() != null || voterUpdateDTO.getMiddleName() != null || voterUpdateDTO.getLastName() != null || voterUpdateDTO.getSuffixName() != null) {
            NameHistory nameHistory = new NameHistory();
            BeanUtils.copyProperties(voter, nameHistory);
            log.info("history saved for voter id : {}", voter.getVoterId());
            nameHistoryRepo.save(nameHistory);
        }
    }
}
