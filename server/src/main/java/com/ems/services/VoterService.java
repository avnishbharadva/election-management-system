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

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterService {

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final PartyService partyService;
    private final AddressService addressService;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException {
        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);
        Set<AddressDTO> addressDTOSet = voterRegisterDTO.getAddress();
        Set<Address> addressSet = globalMapper.toAddressSet(addressDTOSet);

        var savedVoter = voterRepo.save(voter);
        addressSet.forEach(address -> address.setVoter(savedVoter));
        addressRepo.saveAll(addressSet);
        return globalMapper.toVoterRegisterDTO(savedVoter);
    }

    public VoterRegisterDTO findBySSN(String ssnNumber) {
        var voter = voterRepo.findBySsnNumber(ssnNumber).orElseThrow(() -> new VoterNotFoundException("Voter not found with SSN : " + ssnNumber));
        return globalMapper.toVoterRegisterDTO(voter);

//        var voterOptional = voterRepo.findBySsnNumber(ssnNumber);
//        if(voterOptional.isPresent()){
//            var voter = voterOptional.get();
//            log.info("voter with ssn {} : {}",ssnNumber,voter);
//            return voterMapper.toVoterRegisterDTO(voter);
//        }
//        throw new VoterNotFoundException("Voter not found with SSN : " + ssnNumber);
    }

    public VoterRegisterDTO findByDMV(String dmvNumber){
        Optional<Voter> voterOptional = voterRepo.findByDmvNumber(dmvNumber);
        return voterOptional.map(globalMapper::toVoterRegisterDTO).orElse(null);
    }

    public VoterRegisterDTO findByFirstName(String name) {
        Optional<Voter> voterOptional = voterRepo.findByFirstName(name);
        if(voterOptional.isPresent())
            return globalMapper.toVoterRegisterDTO(voterOptional.get());
        return null;
    }
}
