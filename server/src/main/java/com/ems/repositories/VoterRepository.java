package com.ems.repositories;

import com.ems.dtos.VoterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.entities.Voter;
import com.ems.projections.VoterView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {
    boolean existsBySsnNumber(String ssnNumber);
    Optional<VoterResponseDTO> findBySsnNumber(String ssnNumber);
    <T> T findByFirstName(String firstName, Class<T> type);
}
