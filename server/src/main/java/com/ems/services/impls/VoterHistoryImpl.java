package com.ems.services.impls;

import com.ems.entities.Voter;
import com.ems.services.VoterHistoryService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoterHistoryImpl implements VoterHistoryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getVoterHistoryId(String voterId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        return auditReader.createQuery().forRevisionsOfEntity(Voter.class, false, true).add(AuditEntity.id().eq(voterId)).getResultList();
    }
}
