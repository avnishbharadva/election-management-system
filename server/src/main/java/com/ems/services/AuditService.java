package com.ems.services;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;
import org.openapitools.model.AuditDataDTO;

import java.util.List;

public interface AuditService {

//    @EventListener
    void voterAudit(VoterUpdateEvent event);

    List<AuditDataDTO> getAudit(String voterId);
}
