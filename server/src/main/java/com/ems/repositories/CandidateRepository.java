package com.ems.repositories;

import com.ems.entities.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,Long> {

    Optional<Candidate> findByCandidateSSN(String candidateSSN);
    List<Candidate> findByParty_PartyName(String candidatePartyName);
    Page<Candidate> findByElection_electionId(Long electionId, Pageable pageable);
}
