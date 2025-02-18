package com.ems.services;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;

public interface AuditService {

    @EventListener
    void voterAudit(VoterUpdateEvent event);

    void addressAudit(Voter voter, Address oldAddress, Address newAddress);
}
