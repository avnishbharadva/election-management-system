package com.ems.services;

import com.ems.entities.Voter;

public interface AuditService {
    void voterAudit(Voter oldVoter, Voter newVoter);
}
