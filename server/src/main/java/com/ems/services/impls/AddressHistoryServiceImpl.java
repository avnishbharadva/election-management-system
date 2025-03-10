package com.ems.services.impls;

import com.ems.events.AddressUpdateEvent;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.AddressHistoryRepository;
import com.ems.services.AddressHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
//@KafkaListener(topics = "address-update-event-topic",groupId = "update-voter-events-topic")
public class AddressHistoryServiceImpl implements AddressHistoryService {

    private final GlobalMapper globalMapper;
    private final AddressHistoryRepository addressHistoryRepo;
    @Async
    @KafkaHandler
    @Override
    public void addressHistory(AddressUpdateEvent addressUpdateEvent) {
        log.info("history added for : {}", addressUpdateEvent.getVoterId());
//        log.info("history added for : {}", addressUpdateEvent.getAddress().getVoter().getVoterId());
        addressHistoryRepo.save(globalMapper.toAddressHistory(addressUpdateEvent.getAddress()));
    }
}
