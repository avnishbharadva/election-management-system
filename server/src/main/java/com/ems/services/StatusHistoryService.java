package com.ems.services;

import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;

public interface StatusHistoryService{
<<<<<<< HEAD
    @EventListener
=======
//    @EventListener
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    void statusHistory(VoterUpdateEvent voterUpdateEvent);
}
