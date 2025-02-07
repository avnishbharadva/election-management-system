package com.ems.services;

import com.ems.dtos.AddressDTO;
import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Address;
import com.ems.mappers.AddressMapper;
import com.ems.mappers.PartyMapper;
import com.ems.mappers.VoterMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService{

    private final VoterRepository voterRepo;
    private final VoterMapper voterMapper;
    private final PartyMapper partyMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    @Override
    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) {
        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new RuntimeException("Party not found with id : " + voterRegisterDTO.getPartyId()));

        var voter = voterMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);
        List<AddressDTO> addressDTOSet = voterRegisterDTO.getAddress();
        List<Address> addressSet = addressMapper.toAddressSet(addressDTOSet);

        var savedVoter = voterRepo.save(voter);
        addressSet.forEach(address -> address.setVoter(savedVoter));
        addressRepo.saveAll(addressSet);
        return voterMapper.toVoterRegisterDTO(savedVoter);
    }

    @Override
    public List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO) {
        var result = voterRepo.findBy(
                Example.of(voterMapper.toVoter(searchDTO), ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()),
                FluentQuery.FetchableFluentQuery::all
        ).stream().map(voterMapper::toVoterRegisterDTO).toList();
        if (result.isEmpty()) {
            log.warn("⚠️ No voters found for search criteria: {}", searchDTO);
            throw new RuntimeException("No voters found matching the given criteria.");
        }
        return result;
    }
}