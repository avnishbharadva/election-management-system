package com.ems.services.impls;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.exceptions.VoterNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService
{
    @Value("${file.upload-dir}")
    private String uploadDir;
    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    @Override
    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());
        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var addressDTOList = voterRegisterDTO.getAddress();
        var addressList = globalMapper.toAddressList(addressDTOList);

        Files.createDirectories(Paths.get(uploadDir + "/photos"));
        Files.createDirectories(Paths.get(uploadDir + "/signatures"));

        MultipartFile voterImage = voterRegisterDTO.getImage();
        var imagePath = uploadDir + "/photos/" + voterImage.getOriginalFilename();
        voterImage.transferTo(new File(imagePath));
        voter.setImage(imagePath);

        MultipartFile signature = voterRegisterDTO.getSignature();
        var signaturePath = uploadDir + "/signatures/" + signature.getOriginalFilename();
        signature.transferTo(new File(signaturePath));
        voter.setSignature(signaturePath);

        var savedVoter = voterRepo.save(voter);
        addressList.forEach(address -> {
            address.setVoter(savedVoter);
            if (address.getCounty() == null)
                address.setCounty(addressList.getFirst().getCounty());
        });
        addressRepo.saveAll(addressList);
        log.info("voter registration completed for : {}", savedVoter.getSsnNumber());

        return globalMapper.toVoterRegisterDTO(savedVoter);
    }

    @Override
    public Page<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO, int page, int size, String[] sort) {
        return voterRepo.findBy(
                Example.of(globalMapper.toVoter(searchDTO),
                        ExampleMatcher.matching()
                        .withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)),
                query -> query.sortBy(getSort(sort)).page(PageRequest.of(page, size, getSort(sort)))
        ).map(globalMapper::toVoterRegisterDTO);
    }
    private Sort getSort(String[] sort) {
        return (sort == null || sort.length == 0) ? Sort.unsorted()
                : Sort.by(Sort.Direction.fromOptionalString(sort.length > 1 ? sort[1] : "asc").orElse(Sort.Direction.ASC), sort[0]);
    }

    @Override
    public VoterRegisterDTO updateVoter(String id, VoterUpdateDTO voterUpdateDTO) {
        log.info("update process start for {} voter",id);

        var existingVoter = voterRepo.findById(id).orElseThrow(() -> new VoterNotFoundException("Voter not found with id : " + id));

        partyRepo.findById(voterUpdateDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party not found with id : " + voterUpdateDTO.getPartyId()));

        BeanUtils.copyProperties(voterUpdateDTO, existingVoter);

        voterRepo.save(existingVoter);
        log.info("updated voter details of : {}",id);
        return globalMapper.toVoterRegisterDTO(existingVoter);
    }

}
