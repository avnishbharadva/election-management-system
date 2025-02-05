package com.ems.repositories;

import com.ems.entities.CandidateAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateAddressRepository extends JpaRepository<CandidateAddress,Long> {
}
