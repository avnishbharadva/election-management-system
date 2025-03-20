package com.ems.services;

<<<<<<< HEAD
import com.ems.events.AddressUpdateEvent;
=======
import com.ems.events.VoterUpdateEvent;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import org.springframework.context.event.EventListener;

public interface AddressHistoryService {
    @EventListener
<<<<<<< HEAD
    void addressHistory(AddressUpdateEvent addressUpdateEvent);
=======
    void addressHistory(VoterUpdateEvent event);
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
