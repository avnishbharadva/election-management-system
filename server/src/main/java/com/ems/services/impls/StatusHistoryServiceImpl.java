package com.ems.services.impls;

import com.ems.entities.StatusHistory;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import com.ems.repositories.StatusHistoryRepository;
import com.ems.services.StatusHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatusHistoryServiceImpl implements StatusHistoryService {
    private final StatusHistoryRepository statusHistoryRepo;

    @Async("taskExecutor")
//    @KafkaHandler
    @Override
    public void statusHistory(VoterUpdateEvent voterUpdateEvent) {
        Voter oldVoter = voterUpdateEvent.getOldVoter();
        Voter newVoter = voterUpdateEvent.getNewVoter();

        if(!oldVoter.getVoterStatus().equals(newVoter.getVoterStatus())){
            StatusHistory statusHistory = new StatusHistory();
            statusHistory.setVoterId(newVoter.getVoterId());
            statusHistory.setStatus(newVoter.getVoterStatus().getStatusDesc());
            log.info("status history saved for voter id : {}", newVoter.getVoterId());
            statusHistoryRepo.save(statusHistory);
        }
    }
}
