package com.ems.services;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.entities.Address;
import com.ems.entities.Party;
import com.ems.entities.Voter;
import com.ems.mappers.AddressMapper;
import com.ems.mappers.PartyMapper;
import com.ems.mappers.VoterMapper;
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
    private final VoterMapper voterMapper;
    private final PartyService partyService;
    private final PartyMapper partyMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) {
        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new RuntimeException("Party not found with id : " + voterRegisterDTO.getPartyId()));

        var voter = voterMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);
        Set<AddressDTO> addressDTOSet = voterRegisterDTO.getAddress();
        Set<Address> addressSet = addressMapper.toAddressSet(addressDTOSet);

        var savedVoter = voterRepo.save(voter);
        addressSet.forEach(address -> address.setVoter(savedVoter));
        addressRepo.saveAll(addressSet);
        return voterMapper.toVoterRegisterDTO(savedVoter);
    }

    public VoterRegisterDTO findBySSN(String ssnNumber) {
        Optional<Voter> voterOptional = voterRepo.findBySsnNumber(ssnNumber);
        log.info("Voter with {} : {}",ssnNumber,voterOptional.map(voterMapper::toVoterRegisterDTO).orElse(null));
        return voterOptional.map(voterMapper::toVoterRegisterDTO).orElse(null);
    }

    public VoterRegisterDTO findByDMV(String dmvNumber){
        Optional<Voter> voterOptional = voterRepo.findByDmvNumber(dmvNumber);
        return voterOptional.map(voterMapper::toVoterRegisterDTO).orElse(null);
    }

    public VoterRegisterDTO findByFirstName(String name) {
        Optional<Voter> voterOptional = voterRepo.findByFirstName(name);
        if(voterOptional.isPresent())
            return voterMapper.toVoterRegisterDTO(voterOptional.get());
        return null;
    }
}
