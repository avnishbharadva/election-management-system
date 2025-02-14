package com.ems.services.impls;

import com.ems.entities.Audit;
import com.ems.entities.Voter;
import com.ems.mongo.repository.AuditRepository;
import com.ems.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    @Override
    public void voterAudit(Voter oldVoter, Voter newVoter) {
        Map<String, Object> voterMap = new HashMap<>();

        if(!oldVoter.getFirstName().equals(newVoter.getFirstName()))
            voterMap.put("firstName",newVoter.getFirstName());
        if(!oldVoter.getMiddleName().equals(newVoter.getMiddleName()))
            voterMap.put("middleName",newVoter.getMiddleName());
        if(!oldVoter.getLastName().equals(newVoter.getLastName()))
            voterMap.put("lastName",newVoter.getLastName());
        if(!oldVoter.getSuffixName().equals(newVoter.getSuffixName()))
            voterMap.put("suffixName",newVoter.getSuffixName());
        if(!oldVoter.getDateOfBirth().equals(newVoter.getDateOfBirth()))
            voterMap.put("dateOfBirth",newVoter.getDateOfBirth());
        if(!oldVoter.getGender().equals(newVoter.getGender()))
            voterMap.put("gender",newVoter.getGender());
        if(!oldVoter.getDmvNumber().equals(newVoter.getDmvNumber()))
            voterMap.put("dmvNumber",newVoter.getDmvNumber());
        if(!oldVoter.getSsnNumber().equals(newVoter.getSsnNumber()))
            voterMap.put("ssnNumber",newVoter.getSsnNumber());
        if(!oldVoter.getEmail().equals(newVoter.getEmail()))
            voterMap.put("email",newVoter.getEmail());
        if(!oldVoter.getPhoneNumber().equals(newVoter.getPhoneNumber()))
            voterMap.put("phoneNumber",newVoter.getPhoneNumber());
        if(oldVoter.isHasVotedBefore()!=newVoter.isHasVotedBefore())
            voterMap.put("hasVoterBefore",newVoter.isHasVotedBefore());
        if(!Objects.equals(oldVoter.getFirstVotedYear(), newVoter.getFirstVotedYear()))
            voterMap.put("firstVotedYear",newVoter.getFirstVotedYear());
        if(!oldVoter.getParty().equals(newVoter.getParty()))
            voterMap.put("party",newVoter.getParty().getPartyName());
        if(!oldVoter.getSignature().equals(newVoter.getSignature()))
            voterMap.put("signature",newVoter.getSignature());
        if(!oldVoter.getImage().equals(newVoter.getImage()))
            voterMap.put("image",newVoter.getImage());

        Audit audit = new Audit();
        audit.setTableName("Voter");
        audit.setCreatedBy("System");
        audit.setUpdatedBy("System");
        audit.setChangeFields(voterMap);
        auditRepository.save(audit);

        log.info("Voter audited : {}",voterMap);
    }
}
