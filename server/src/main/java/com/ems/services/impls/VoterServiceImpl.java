package com.ems.services.impls;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.dtos.VoterUpdateDTO;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.VoterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final ObjectMapper objectMapper;

    @Override
    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException, IOException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());
        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var addressDTOList = voterRegisterDTO.getAddress();
        var addressList = globalMapper.toAddressList(addressDTOList);

//        Files.createDirectories(Paths.get(uploadDir + "/photos"));
//        Files.createDirectories(Paths.get(uploadDir + "/signatures"));
//
//        MultipartFile voterImage = voterRegisterDTO.getImage();
//        var imagePath = uploadDir + "/photos/" + voterImage.getOriginalFilename();
//        voterImage.transferTo(new File(imagePath));
//        voter.setImage(imagePath);
//
//        MultipartFile signature = voterRegisterDTO.getSignature();
//        var signaturePath = uploadDir + "/signatures/" + signature.getOriginalFilename();
//        signature.transferTo(new File(signaturePath));
//        voter.setSignature(signaturePath);

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
    public Page<VoterRegisterDTO> searchVoters(String firstName, String lastName, LocalDate dateOfBirth, String dmvNumber, String ssnNumber, int page, int size, String[] sort) {
        VoterSearchDTO searchDTO = new VoterSearchDTO(firstName, lastName, dateOfBirth, dmvNumber, ssnNumber);
        log.info("this is {}", searchDTO);
        return voterRepo.findBy(
                Example.of(globalMapper.toVoter(searchDTO),
                        ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)),
                query -> query.sortBy(getSort(sort)).page(PageRequest.of(page, size, getSort(sort)))
        ).map(globalMapper::toVoterRegisterDTO);
    }

    private Sort getSort(String[] sort) {
        return (sort == null || sort.length == 0) ? Sort.unsorted()
                : Sort.by(Sort.Direction.fromOptionalString(sort.length > 1 ? sort[1] : "asc").orElse(Sort.Direction.ASC), sort[0]);
    }

    @Override
    public VoterUpdateDTO updateVoter(String id, VoterUpdateDTO voterUpdateDTO, String modifiedBy) {
        return null;
    }

}
