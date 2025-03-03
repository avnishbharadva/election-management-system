package com.ems.services;

import com.ems.events.AddressUpdateEvent;
import org.springframework.context.event.EventListener;

public interface AddressHistoryService {
    @EventListener
    void addressHistory(AddressUpdateEvent addressUpdateEvent);
}
