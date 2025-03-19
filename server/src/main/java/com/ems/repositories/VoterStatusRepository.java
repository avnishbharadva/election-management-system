package com.ems.repositories;

import com.ems.entities.VoterStatus;
import org.openapitools.model.StatusHistoryDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterStatusRepository extends JpaRepository<VoterStatus, Long> {
}
