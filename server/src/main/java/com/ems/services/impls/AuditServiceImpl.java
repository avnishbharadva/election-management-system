package com.ems.services.impls;

import com.ems.entities.Address;
import com.ems.entities.Audit;
import com.ems.entities.Voter;
import com.ems.events.VoterUpdateEvent;
import com.ems.exceptions.DataNotFoundException;
import com.ems.mappers.GlobalMapper;
import com.ems.mongo.repository.AuditRepository;
import com.ems.services.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AuditDataDTO;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@KafkaListener(topics = "update-voter-events-topic" , groupId="update-voter-events-topic")
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;
    private final GlobalMapper mapper;

    @Async("taskExecutor")
    @KafkaHandler
    @Override
    public void voterAudit(VoterUpdateEvent event) {
        var oldVoter = event.getOldVoter();
        var newVoter = event.getNewVoter();
        var fields = getUpdatedFields(oldVoter, newVoter, event.getOldAddress(), event.getNewAddress());
        if (!fields.isEmpty()) {
            Audit audit = getAudit(newVoter, fields);
            auditRepository.save(audit);
            log.info("thread - {}, Voter audited: {}", Thread.currentThread().getName(),fields);
        }
    }

    @Override
    public List<AuditDataDTO> getAudit(String voterId) {
        log.info("audit called for voterId : {}", voterId);
        var auditList = auditRepository.findByVoterId(voterId);
        if(auditList.isEmpty())
            throw new DataNotFoundException("Voter Id not found : " + voterId);
        return mapper.toAuditDataDTO(auditList);
    }

    private List<Map<String, Object>> getUpdatedFields(Voter oldVoter, Voter newVoter, List<Address> oldAddressList, List<Address> newAddressList) {
        Map<String, Object> updateField = new HashMap<>();
        Map<String, Object> oldField = new HashMap<>();

        compareAndPut(oldField,updateField, "firstName", oldVoter.getFirstName(), newVoter.getFirstName());
        compareAndPut(oldField,updateField, "middleName", oldVoter.getMiddleName(), newVoter.getMiddleName());
        compareAndPut(oldField,updateField, "lastName", oldVoter.getLastName(), newVoter.getLastName());
        compareAndPut(oldField,updateField, "suffixName", oldVoter.getSuffixName(), newVoter.getSuffixName());
        compareAndPut(oldField,updateField, "dateOfBirth", oldVoter.getDateOfBirth(), newVoter.getDateOfBirth());
        compareAndPut(oldField,updateField, "gender", oldVoter.getGender(), newVoter.getGender());
        compareAndPut(oldField,updateField, "dmvNumber", oldVoter.getDmvNumber(), newVoter.getDmvNumber());
        compareAndPut(oldField,updateField, "ssnNumber", oldVoter.getSsnNumber(), newVoter.getSsnNumber());
        compareAndPut(oldField,updateField, "email", oldVoter.getEmail(), newVoter.getEmail());
        compareAndPut(oldField,updateField, "phoneNumber", oldVoter.getPhoneNumber(), newVoter.getPhoneNumber());
        compareAndPut(oldField,updateField, "firstVotedYear", oldVoter.getFirstVotedYear(), newVoter.getFirstVotedYear());
        compareAndPut(oldField,updateField, "signature", oldVoter.getSignature(), newVoter.getSignature());
        compareAndPut(oldField,updateField, "image", oldVoter.getImage(), newVoter.getImage());

        if (oldVoter.isHasVotedBefore() != newVoter.isHasVotedBefore()) {
            updateField.put("hasVoterBefore", newVoter.isHasVotedBefore());
            oldField.put("hasVoterBefore", oldVoter.isHasVotedBefore());
        }

        if (!oldVoter.getParty().equals(newVoter.getParty())) {
                updateField.put("party", newVoter.getParty().getPartyName());
                oldField.put("party", oldVoter.getParty().getPartyName());
        }

        if (!oldVoter.getVoterStatus().equals(newVoter.getVoterStatus())) {
            updateField.put("status", newVoter.getVoterStatus().getStatusDesc());
            oldField.put("status", oldVoter.getVoterStatus().getStatusDesc());
        }

        List<Map<String, Object>> addressFields;

        for (int i = 0; i < Math.min(oldAddressList.size(), newAddressList.size()); i++) {
            Address oldAddress = oldAddressList.get(i);
            Address newAddress = newAddressList.get(i);

            addressFields = getAddressUpdatedFields(oldAddress, newAddress);
            log.info("iteration {} : {}",i,addressFields);
            if (!addressFields.isEmpty()) {
                if(i==0 && !addressFields.get(0).isEmpty()){
                    oldField.put("residentialAddress", addressFields.get(0));
                    updateField.put("residentialAddress", addressFields.get(1));
                    log.info("residential address change : {}", addressFields);
                }
                if(i==1 && !addressFields.get(0).isEmpty()){
                    oldField.put("mailingAddress", addressFields.get(0));
                    updateField.put("mailingAddress", addressFields.get(1));
                    log.info("mailing address change : {}",addressFields);
                }
            }
        }

        return List.of(oldField, updateField);
    }

    public static List<Map<String, Object>> getAddressUpdatedFields(Address oldAddress, Address newAddress) {
        Map<String, Object> updatedFields = new HashMap<>();
        Map<String, Object> oldFields = new HashMap<>();

        if (!Objects.equals(oldAddress.getAddressLine(), newAddress.getAddressLine())) {
            updatedFields.put("addressLine", newAddress.getAddressLine());
            oldFields.put("addressLine", oldAddress.getAddressLine());
        }
        if (!Objects.equals(oldAddress.getAptNumber(), newAddress.getAptNumber())) {
            updatedFields.put("aptNumber", newAddress.getAptNumber());
            oldFields.put("aptNumber", oldAddress.getAptNumber());
        }
        if (!Objects.equals(oldAddress.getCity(), newAddress.getCity())) {
            updatedFields.put("city", newAddress.getCity());
            oldFields.put("city",oldAddress.getCity());
        }
        if (!Objects.equals(oldAddress.getCounty(), newAddress.getCounty())) {
            updatedFields.put("county", newAddress.getCounty());
            oldFields.put("county",oldAddress.getCounty());
        }
        if(!Objects.equals(oldAddress.getTown(), newAddress.getTown())){
            updatedFields.put("town", newAddress.getTown());
            oldFields.put("town",oldAddress.getTown());
        }
        if (!Objects.equals(oldAddress.getState(), newAddress.getState())) {
            updatedFields.put("state", newAddress.getState());
            oldFields.put("state",oldAddress.getState());
        }
        if (!Objects.equals(oldAddress.getZipCode(), newAddress.getZipCode())) {
            updatedFields.put("zipCode", newAddress.getZipCode());
            oldFields.put("zipCode",oldAddress.getZipCode());
        }

        return List.of(oldFields,updatedFields);
    }

    private <T> void compareAndPut(Map<String, Object> oldField,Map<String, Object> fieldMap, String fieldName, T oldValue, T newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            fieldMap.put(fieldName, newValue);
            oldField.put(fieldName, oldValue);
        }
    }

    private static Audit getAudit(Voter newVoter, List<Map<String, Object>> fieldList) {
        Audit audit = new Audit();
        audit.setTableName("Voter");
        audit.setVoterId(newVoter.getVoterId());
        audit.setOldFields(fieldList.get(0));
        audit.setChangeFields(fieldList.get(1));
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        audit.setCreatedBy(userName);
        audit.setUpdatedBy(userName);
        return audit;
    }
}