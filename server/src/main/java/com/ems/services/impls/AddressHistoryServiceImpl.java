package com.ems.services.impls;

<<<<<<< HEAD
import com.ems.events.AddressUpdateEvent;
=======
import com.ems.entities.Address;
import com.ems.events.VoterUpdateEvent;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressHistoryRepository;
import com.ems.services.AddressHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "address-update-event-topic",groupId = "update-voter-events-topic")
=======
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
//@KafkaListener(topics = "address-update-event-topic",groupId = "update-voter-events-topic")
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
public class AddressHistoryServiceImpl implements AddressHistoryService {

    private final GlobalMapper globalMapper;
    private final AddressHistoryRepository addressHistoryRepo;
<<<<<<< HEAD
    @Async
    @KafkaHandler
    @Override
    public void addressHistory(AddressUpdateEvent addressUpdateEvent) {
        log.info("history added for : {}", addressUpdateEvent.getAddress().getVoter().getVoterId());
        addressHistoryRepo.save(globalMapper.toAddressHistory(addressUpdateEvent.getAddress()));
=======

    @Async("taskExecutor")
    @KafkaHandler
    @Override
    public void addressHistory(VoterUpdateEvent event){

        List<Address> oldAddress = event.getOldAddress();
        List<Address> newAddress = event.getNewAddress();

        if(!oldAddress.get(0).equals(newAddress.get(0)))
            addressHistoryRepo.save(globalMapper.toAddressHistory(event.getNewVoter().getResidentialAddress()));

        if(!oldAddress.get(1).equals(newAddress.get(1)))
            addressHistoryRepo.save(globalMapper.toAddressHistory(event.getNewVoter().getMailingAddress()));
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    }
}
