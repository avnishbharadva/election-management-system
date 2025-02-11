package com.ems.services.impls;

import com.ems.dtos.ElectionDTO;
import com.ems.dtos.ElectionSortDTO;
import com.ems.entities.Election;
import com.ems.exceptions.ElectionNotFoundException;
import com.ems.mappers.CandidateMapper;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.ElectionRepository;
import com.ems.services.ElectionService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
public class ElectionServiceImpl implements ElectionService {
    private final ElectionRepository electionRepository;
    private final GlobalMapper globalMapper;
    private final CandidateMapper candidateMapper;

    @Override
    public Election saveElection(ElectionDTO electionDTO) {
        var election=globalMapper.toElectionDTO(electionDTO);
        return electionRepository.save(election);
    }

    @Override
    public Election updateElection(Long electionId, ElectionDTO electionDTO) {
        var existingElection=electionRepository.findById(electionId)
                .orElseThrow(()->new ElectionNotFoundException("No such election with id:"+electionId));

        Optional.ofNullable(electionDTO.getElectionName()).ifPresent(existingElection::setElectionName);
        Optional.ofNullable(electionDTO.getElectionDate()).ifPresent(existingElection::setElectionDate);
        Optional.ofNullable(electionDTO.getElectionType()).ifPresent(existingElection::setElectionType);
        Optional.ofNullable(electionDTO.getElectionState()).ifPresent(existingElection::setElectionState);

        if(electionDTO.getTotalSeats()!=0)
            existingElection.setTotalSeats(electionDTO.getTotalSeats());

        return electionRepository.save(existingElection);
    }


    @Override
    public List<ElectionSortDTO> getElectionsSorted(String order) {
        List<Election> elections;

        if ("desc".equalsIgnoreCase(order)) {
            elections = electionRepository.findAllByOrderByElectionDateDesc();
        } else {
            elections = electionRepository.findAllByOrderByElectionDateAsc();
        }
        return elections.stream().map(candidateMapper::toElectionSortDTO).collect(Collectors.toList());
    }

}
