package com.ems.repositories;

import com.ems.entities.VoterStatus;
import jakarta.validation.constraints.Size;
import org.openapitools.model.StatusHistoryDataDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoterStatusRepository extends JpaRepository<VoterStatus, Long> {
    Optional<VoterStatus> findByStatusDesc(String status);
}
