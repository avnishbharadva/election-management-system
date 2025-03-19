// package com.ems.services.impls;

// import com.ems.entities.Address;
// import com.ems.entities.Audit;
// import com.ems.entities.Voter;
// import com.ems.events.AddressUpdateEvent;
// import com.ems.events.VoterUpdateEvent;
// import com.ems.mongo.repository.AuditRepository;
// import com.ems.services.AuditService;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.kafka.annotation.KafkaHandler;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.support.SendResult;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.Objects;
// import java.util.concurrent.CompletableFuture;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// //@KafkaListener(topics = "update-voter-events-topic" , groupId="update-voter-events-topic")
// public class AuditServiceImpl implements AuditService {

//     // private final KafkaTemplate<String,AddressUpdateEvent> kafkaTemplate;
//     private final AuditRepository auditRepository;

//     @Async
//     // @KafkaHandler
//     @Override
//     public void voterAudit(VoterUpdateEvent event) {
//         var oldVoter = event.getOldVoter();
//         var newVoter = event.getNewVoter();
//         var fields = getUpdatedFields(oldVoter, newVoter, event.getOldAddress(), event.getNewAddress());

//         if (!fields.isEmpty()) {
//             Audit audit = getAudit(newVoter, fields);
//             auditRepository.save(audit);
//             log.info("thread - {}, Voter audited: {}", Thread.currentThread().getName(),fields);
//         }
//     }

//     private List<Map<String, Object>> getUpdatedFields(Voter oldVoter, Voter newVoter, List<Address> oldAddressList, List<Address> newAddressList) {
//         Map<String, Object> updateField = new HashMap<>();
//         Map<String, Object> oldField = new HashMap<>();

//         compareAndPut(oldField,updateField, "firstName", oldVoter.getFirstName(), newVoter.getFirstName());
//         compareAndPut(oldField,updateField, "middleName", oldVoter.getMiddleName(), newVoter.getMiddleName());
//         compareAndPut(oldField,updateField, "lastName", oldVoter.getLastName(), newVoter.getLastName());
//         compareAndPut(oldField,updateField, "suffixName", oldVoter.getSuffixName(), newVoter.getSuffixName());
//         compareAndPut(oldField,updateField, "dateOfBirth", oldVoter.getDateOfBirth(), newVoter.getDateOfBirth());
//         compareAndPut(oldField,updateField, "gender", oldVoter.getGender(), newVoter.getGender());
//         compareAndPut(oldField,updateField, "dmvNumber", oldVoter.getDmvNumber(), newVoter.getDmvNumber());
//         compareAndPut(oldField,updateField, "ssnNumber", oldVoter.getSsnNumber(), newVoter.getSsnNumber());
//         compareAndPut(oldField,updateField, "email", oldVoter.getEmail(), newVoter.getEmail());
//         compareAndPut(oldField,updateField, "phoneNumber", oldVoter.getPhoneNumber(), newVoter.getPhoneNumber());
//         compareAndPut(oldField,updateField, "firstVotedYear", oldVoter.getFirstVotedYear(), newVoter.getFirstVotedYear());
//         compareAndPut(oldField,updateField, "signature", oldVoter.getSignature(), newVoter.getSignature());
//         compareAndPut(oldField,updateField, "image", oldVoter.getImage(), newVoter.getImage());

//         if (oldVoter.isHasVotedBefore() != newVoter.isHasVotedBefore()) {
//             updateField.put("hasVoterBefore", newVoter.isHasVotedBefore());
//             oldField.put("hasVoterBefore", oldVoter.isHasVotedBefore());
//         }

//         if (oldVoter.getParty() != null && newVoter.getParty() != null) {
//             if (!oldVoter.getParty().equals(newVoter.getParty())) {
//                 updateField.put("party", newVoter.getParty().getPartyName());
//                 oldField.put("party", oldVoter.getParty().getPartyName());
//             }
//         } else if (oldVoter.getParty() == null && newVoter.getParty() != null) {
//             updateField.put("party", newVoter.getParty().getPartyName());
//             oldField.put("party", "None");
//         }
//         if (oldVoter.getVoterStatus() != null && newVoter.getVoterStatus() != null &&
//                 !oldVoter.getVoterStatus().equals(newVoter.getVoterStatus())) {

//             updateField.put("status", newVoter.getVoterStatus().getStatusDesc());
//             oldField.put("status", oldVoter.getVoterStatus().getStatusDesc());
//         }

//         List<Map<String, Object>> addressFields;

//         for (int i = 0; i < Math.min(oldAddressList.size(), newAddressList.size()); i++) {
//             Address oldAddress = oldAddressList.get(i);
//             Address newAddress = newAddressList.get(i);

//             addressFields = getAddressUpdatedFields(oldAddress, newAddress);
//             log.info("iteration {} : {}",i,addressFields);
//             if (!addressFields.isEmpty()) {
//                 CompletableFuture<SendResult<String,AddressUpdateEvent>> future=kafkaTemplate.send("address-update-event-topic","Address",new AddressUpdateEvent(newAddress));
//                 future.whenComplete((result,exception)->{
//                     if(exception!=null){
//                         log.error("Failed to send message:"+exception.getMessage());
//                     }
//                     else{
//                         log.info("Message sent successfully:"+result.getRecordMetadata());
//                     }
//                 });
//                 if(i==0 && !addressFields.get(0).isEmpty()){
//                     oldField.put("residentialAddress", addressFields.get(0));
//                     updateField.put("residentialAddress", addressFields.get(1));
//                     log.info("residential address change : {}", addressFields);
//                 }
//                 if(i==1 && !addressFields.get(0).isEmpty()){
//                     oldField.put("mailingAddress", addressFields.get(0));
//                     updateField.put("mailingAddress", addressFields.get(1));
//                     log.info("mailing address change : {}",addressFields);
//                 }
//             }
//         }

//         return List.of(oldField, updateField);
//     }

//     public static List<Map<String, Object>> getAddressUpdatedFields(Address oldAddress, Address newAddress) {
//         Map<String, Object> updatedFields = new HashMap<>();
//         Map<String, Object> oldFields = new HashMap<>();

//         if (!Objects.equals(oldAddress.getAddressLine(), newAddress.getAddressLine())) {
//             updatedFields.put("addressLine", newAddress.getAddressLine());
//             oldFields.put("addressLine", oldAddress.getAddressLine());
//         }
//         if (!Objects.equals(oldAddress.getAptNumber(), newAddress.getAptNumber())) {
//             updatedFields.put("aptNumber", newAddress.getAptNumber());
//             oldFields.put("aptNumber", oldAddress.getAptNumber());
//         }
//         if (!Objects.equals(oldAddress.getCity(), newAddress.getCity())) {
//             updatedFields.put("city", newAddress.getCity());
//             oldFields.put("city",oldAddress.getCity());
//         }
//         if (!Objects.equals(oldAddress.getCounty(), newAddress.getCounty())) {
//             updatedFields.put("county", newAddress.getCounty());
//             oldFields.put("county",oldAddress.getCounty());
//         }
//         if (!Objects.equals(oldAddress.getState(), newAddress.getState())) {
//             updatedFields.put("state", newAddress.getState());
//             oldFields.put("state",oldAddress.getState());
//         }
//         if (!Objects.equals(oldAddress.getZipCode(), newAddress.getZipCode())) {
//             updatedFields.put("zipCode", newAddress.getZipCode());
//             oldFields.put("zipCode",oldAddress.getZipCode());
//         }
//         if (!Objects.equals(oldAddress.getAddressType(), newAddress.getAddressType())) {
//             updatedFields.put("addressType", newAddress.getAddressType());
//             oldFields.put("addressType",oldAddress.getAddressType());
//         }

//         return List.of(oldFields,updatedFields);
//     }

//     private <T> void compareAndPut(Map<String, Object> oldField,Map<String, Object> fieldMap, String fieldName, T oldValue, T newValue) {
//         if (!Objects.equals(oldValue, newValue)) {
//             fieldMap.put(fieldName, newValue);
//             oldField.put(fieldName, oldValue);
//         }
//     }

//     private static Audit getAudit(Voter newVoter, List<Map<String, Object>> fieldList) {
//         Map<String,Object> voterData = Map.ofEntries(
//                 Map.entry("voterId", newVoter.getVoterId()),
//                 Map.entry("dateOfBirth", newVoter.getDateOfBirth()),
//                 Map.entry("ssnNumber", newVoter.getSsnNumber()),
//                 Map.entry("dmvNumber", newVoter.getDmvNumber()),
//                 Map.entry("email", newVoter.getEmail())
//         );

//         Audit audit = new Audit();
//         audit.setTableName("Voter");
//         audit.setMetadata(voterData);
//         audit.setOldFields(fieldList.get(0));
//         audit.setChangeFields(fieldList.get(1));
//         audit.setCreatedBy("SYSTEM");
//         audit.setUpdatedBy("SYSTEM");
//         return audit;
//     }
// }
