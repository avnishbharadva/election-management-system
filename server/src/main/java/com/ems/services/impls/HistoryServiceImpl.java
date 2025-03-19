package com.ems.services.impls;

import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressHistoryRepository;
import com.ems.repositories.NameHistoryRepository;
import com.ems.repositories.StatusHistoryRepository;
import com.ems.services.HistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AddressHistoryDataDTO;
import org.openapitools.model.NameHistoryDataDTO;
import org.openapitools.model.StatusHistoryDataDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final NameHistoryRepository nameHistoryRepo;
    private final StatusHistoryRepository statusHistoryRepo;
    private final AddressHistoryRepository addressHistoryRepo;
    private final GlobalMapper mapper;

    @Override
    public List<NameHistoryDataDTO> getNameHistory(String voterId) {
        log.info("name history for voterId : {}", voterId);
        var nameHistoryList = nameHistoryRepo.findByVoterId(voterId);
        if(nameHistoryList.isEmpty())
            throw new DataNotFoundException("Voter not found with voterId : " + voterId);
        return mapper.toNameHistoryDataDTO(nameHistoryList);
    }

    @Override
    public List<StatusHistoryDataDTO> getStatusHistory(String voterId) {
        log.info("status history for voterId : {}", voterId);
        var statusHistoryList = statusHistoryRepo.findByVoterId(voterId);
        if(statusHistoryList.isEmpty())
            throw new DataNotFoundException("Voter not found with voterId : " + voterId);
        return mapper.toStatusHistoryDataDTO(statusHistoryList);
    }

    @Override
    public List<AddressHistoryDataDTO> getAddressHistory(String voterId) {
        log.info("address history for voterId : {}", voterId);
        var addressHistoryList = addressHistoryRepo.findByVoterId(voterId);
        if(addressHistoryList.isEmpty())
            throw new DataNotFoundException("Voter not found with voterId : " + voterId);
        return mapper.toAddressHistoryDataDTO(addressHistoryList);
    }
}
