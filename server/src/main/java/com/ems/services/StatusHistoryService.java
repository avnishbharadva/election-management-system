package com.ems.services;

import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;

public interface StatusHistoryService{
//    @EventListener
    void statusHistory(VoterUpdateEvent voterUpdateEvent);
}
