package com.ems.mongo.repository;

import com.ems.entities.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRepository extends MongoRepository<Audit, String> {
    List<Audit> findByVoterId(String voterId);
}
