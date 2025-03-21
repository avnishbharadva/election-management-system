package com.ems.services.impls;

import com.ems.entities.NameHistory;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import com.ems.mappers.GlobalMapper;
import com.ems.repositories.NameHistoryRepository;
import com.ems.services.NameHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NameHistoryServiceImpl implements NameHistoryService {

    private final GlobalMapper globalMapper;
    private final NameHistoryRepository nameHistoryRepo;

    @Async("taskExecutor")
    @KafkaHandler
    @Override
    public void nameHistory(VoterUpdateEvent voterUpdateEvent) {
        Voter oldVoter = voterUpdateEvent.getOldVoter();
        Voter newVoter = voterUpdateEvent.getNewVoter();

        if(!oldVoter.getFirstName().equals(newVoter.getFirstName()) || !oldVoter.getMiddleName().equals(newVoter.getMiddleName()) || !oldVoter.getLastName().equals(newVoter.getLastName()) || !oldVoter.getSuffixName().equals(newVoter.getSuffixName())){
            NameHistory nameHistory = globalMapper.toNameHistory(newVoter);
            log.info("Thread {} - name history saved for voter id : {}", Thread.currentThread().getName(),newVoter.getVoterId());
            nameHistoryRepo.save(nameHistory);
        }
    }
}
