package com.ems.services.impls;

import com.ems.entities.Address;
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
        var fieldMap = new HashMap<String, Object>();

        if(!oldVoter.getFirstName().equals(newVoter.getFirstName()))
            fieldMap.put("firstName",newVoter.getFirstName());
        if(!oldVoter.getMiddleName().equals(newVoter.getMiddleName()))
            fieldMap.put("middleName",newVoter.getMiddleName());
        if(!oldVoter.getLastName().equals(newVoter.getLastName()))
            fieldMap.put("lastName",newVoter.getLastName());
        if(!oldVoter.getSuffixName().equals(newVoter.getSuffixName()))
            fieldMap.put("suffixName",newVoter.getSuffixName());
        if(!oldVoter.getDateOfBirth().equals(newVoter.getDateOfBirth()))
            fieldMap.put("dateOfBirth",newVoter.getDateOfBirth());
        if(!oldVoter.getGender().equals(newVoter.getGender()))
            fieldMap.put("gender",newVoter.getGender());
        if(!oldVoter.getDmvNumber().equals(newVoter.getDmvNumber()))
            fieldMap.put("dmvNumber",newVoter.getDmvNumber());
        if(!oldVoter.getSsnNumber().equals(newVoter.getSsnNumber()))
            fieldMap.put("ssnNumber",newVoter.getSsnNumber());
        if(!oldVoter.getEmail().equals(newVoter.getEmail()))
            fieldMap.put("email",newVoter.getEmail());
        if(!oldVoter.getPhoneNumber().equals(newVoter.getPhoneNumber()))
            fieldMap.put("phoneNumber",newVoter.getPhoneNumber());
        if(oldVoter.isHasVotedBefore()!=newVoter.isHasVotedBefore())
            fieldMap.put("hasVoterBefore",newVoter.isHasVotedBefore());
        if(!Objects.equals(oldVoter.getFirstVotedYear(), newVoter.getFirstVotedYear()))
            fieldMap.put("firstVotedYear",newVoter.getFirstVotedYear());
        if(!oldVoter.getParty().equals(newVoter.getParty()))
            fieldMap.put("party",newVoter.getParty().getPartyName());
        if(!oldVoter.getSignature().equals(newVoter.getSignature()))
            fieldMap.put("signature",newVoter.getSignature());
        if(!oldVoter.getImage().equals(newVoter.getImage()))
            fieldMap.put("image",newVoter.getImage());

        if(!fieldMap.isEmpty()){
            Audit audit = getAudit(newVoter, fieldMap);
            auditRepository.save(audit);
            log.info("Voter audited : {}",fieldMap);
        }
    }

    @Override
    public void addressAudit(Voter voter, Address oldAddress, Address newAddress) {
        var fieldMap = new HashMap<String, Object>();

        if(!oldAddress.getAddressLine().equals(newAddress.getAddressLine()))
            fieldMap.put("addressLine",newAddress.getAddressLine());
        if(!oldAddress.getAptNumber().equals(newAddress.getAptNumber()))
            fieldMap.put("aptNumber", newAddress.getAptNumber());
        if(!oldAddress.getCity().equals(newAddress.getCity()))
            fieldMap.put("city",newAddress.getCity());
        if(!oldAddress.getCounty().equals(newAddress.getCounty()))
            fieldMap.put("county",newAddress.getCounty());
        if(!oldAddress.getZipCode().equals(newAddress.getZipCode()))
            fieldMap.put("zipCode",newAddress.getZipCode());
        if(!oldAddress.getAddressType().equals(newAddress.getAddressType()))
            fieldMap.put("addressType",newAddress.getAddressType());

        if(!fieldMap.isEmpty()){
            Audit audit = getAudit(voter, fieldMap);
            auditRepository.save(audit);
        }
    }

    private static Audit getAudit(Voter newVoter, HashMap<String, Object> fieldMap) {
        Map<String,Object> voterData = Map.ofEntries(
                Map.entry("voterId", newVoter.getVoterId()),
                Map.entry("dateOfBirth", newVoter.getDateOfBirth()),
                Map.entry("ssnNumber", newVoter.getSsnNumber()),
                Map.entry("dmvNumber", newVoter.getDmvNumber()),
                Map.entry("email", newVoter.getEmail())
        );

        Audit audit = new Audit();
        audit.setTableName("Voter");
        audit.setMetadata(voterData);
        audit.setChangeFields(fieldMap);
        audit.setCreatedBy("SYSTEM");
        audit.setUpdatedBy("SYSTEM");
        return audit;
    }
}
