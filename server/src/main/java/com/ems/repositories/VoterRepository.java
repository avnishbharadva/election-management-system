package com.ems.repositories;

import com.ems.entities.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> , QueryByExampleExecutor<Voter> {
    Optional<Voter> findBySsnNumber(String ssnNumber);
    Optional<Voter> findByDmvNumber(String dmvNumber);
    Optional<Voter> findByFirstName(String name);



}
