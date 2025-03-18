package com.ems.services.impls;

import com.ems.entities.Address;
import com.ems.events.VoterUpdateEvent;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressHistoryRepository;
import com.ems.services.AddressHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
//@KafkaListener(topics = "address-update-event-topic",groupId = "update-voter-events-topic")
public class AddressHistoryServiceImpl implements AddressHistoryService {

    private final GlobalMapper globalMapper;
    private final AddressHistoryRepository addressHistoryRepo;

    @Async("taskExecutor")
//    @KafkaHandler
    @Override
    public void addressHistory(VoterUpdateEvent event){

        List<Address> oldAddress = event.getOldAddress();
        List<Address> newAddress = event.getNewAddress();

        if(!oldAddress.get(0).equals(newAddress.get(0)))
            addressHistoryRepo.save(globalMapper.toAddressHistory(event.getNewVoter().getResidentialAddress()));

        if(!oldAddress.get(1).equals(newAddress.get(1)))
            addressHistoryRepo.save(globalMapper.toAddressHistory(event.getNewVoter().getMailingAddress()));
    }
}
