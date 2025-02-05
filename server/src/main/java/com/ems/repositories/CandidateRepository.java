package com.ems.repositories;

import com.ems.entities.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

    Optional<Candidate> findByCandidateSSN(String candidateSSN);
}
