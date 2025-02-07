package com.ems.services;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.exceptions.VoterNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterService {

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException {
        log.info("voter registration start for : {}",voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var addressDTOList = voterRegisterDTO.getAddress();
        var addressList = globalMapper.toAddressList(addressDTOList);

        var savedVoter = voterRepo.save(voter);
        addressList.forEach(address -> {
            address.setVoter(savedVoter);
            if(address.getCounty()==null)
                address.setCounty(addressList.getFirst().getCounty());
        });
        addressRepo.saveAll(addressList);
        log.info("voter registration completed for : {}",savedVoter.getSsnNumber());
        return globalMapper.toVoterRegisterDTO(savedVoter);
    }

    public VoterRegisterDTO findBySSN(String ssnNumber) {
        log.info("searching start for ssn : {}",ssnNumber);
        return voterRepo.findBySsnNumber(ssnNumber).map(globalMapper::toVoterRegisterDTO).orElseThrow((() -> new VoterNotFoundException("Voter not exist with SSN No : " + ssnNumber)));
    }

    public VoterRegisterDTO findByDMV(String dmvNumber){
        log.info("searching start for DMV No : {}",dmvNumber);
        return voterRepo.findByDmvNumber(dmvNumber).map(globalMapper::toVoterRegisterDTO).orElseThrow(() -> new VoterNotFoundException("Voter not exist with DMV No : " + dmvNumber));
    }

    public VoterRegisterDTO findByFirstName(String name) {
        log.info("searching start for first name : {}",name);
        return voterRepo.findByFirstName(name).map(globalMapper::toVoterRegisterDTO).orElseThrow(() -> new VoterNotFoundException("Voter not found with first name : " + name));
    }
}
