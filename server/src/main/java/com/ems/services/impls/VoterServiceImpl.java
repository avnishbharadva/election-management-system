package com.ems.services.impls;

import com.ems.dtos.VoterRegisterDTO;
import com.ems.dtos.VoterSearchDTO;
import com.ems.entities.Voter;
import com.ems.exceptions.PartyNotFoundException;
import com.ems.exceptions.VoterNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressRepository;
import com.ems.repositories.PartyRepository;
import com.ems.repositories.VoterRepository;
import com.ems.services.VoterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoterServiceImpl implements VoterService
{

    private final VoterRepository voterRepo;
    private final GlobalMapper globalMapper;
    private final AddressRepository addressRepo;
    private final PartyRepository partyRepo;

    @Override
    public VoterRegisterDTO register(VoterRegisterDTO voterRegisterDTO) throws PartyNotFoundException {
        log.info("voter registration start for : {}", voterRegisterDTO.getSsnNumber());

        var party = partyRepo.findById(voterRegisterDTO.getPartyId()).orElseThrow(() -> new PartyNotFoundException("Party Not Found with ID : " + voterRegisterDTO.getPartyId()));

        var voter = globalMapper.toVoter(voterRegisterDTO);
        voter.setParty(party);

        var addressDTOList = voterRegisterDTO.getAddress();
        var addressList = globalMapper.toAddressList(addressDTOList);

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
    public List<VoterRegisterDTO> searchVoters(VoterSearchDTO searchDTO) {
        var voters = voterRepo.findBy(Example.of(globalMapper.toVoter(searchDTO), ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()), FluentQuery.FetchableFluentQuery::all).stream().map(globalMapper::toVoterRegisterDTO).toList();

        if (voters.isEmpty()) {
            log.warn("No voters found for search criteria: {}", searchDTO);
            throw new VoterNotFoundException("No voters found matching the given criteria.");
        }
        return voters;
    }

}
