package com.ems.repositories;

import com.ems.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {
    Optional<Voter> findBySsnNumber(String ssnNumber);
    Optional<Voter> findByDmvNumber(String dmvNumber);

    Optional<Voter> findByFirstName(String name);
}
