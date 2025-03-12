package com.ems.services;

import com.ems.events.AddressUpdateEvent;
import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;

public interface AddressHistoryService {
    @EventListener
    void addressHistory(VoterUpdateEvent event);
}
