package com.ems.services;

import com.ems.entities.Address;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import org.springframework.context.event.EventListener;
import org.openapitools.model.AuditDataDTO;

import java.util.List;

public interface AuditService {

<<<<<<< HEAD
    @EventListener
=======
//    @EventListener
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
    void voterAudit(VoterUpdateEvent event);

    List<AuditDataDTO> getAudit(String voterId);
}
