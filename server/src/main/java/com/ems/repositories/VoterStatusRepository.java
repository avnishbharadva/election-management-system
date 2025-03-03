package com.ems.repositories;

import com.ems.entities.VoterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoterStatusRepository extends JpaRepository<VoterStatus, Long> {
}
