package com.ems.repositories;

import com.ems.entities.AddressHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressHistoryRepository extends JpaRepository<AddressHistory, Long> {
    List<AddressHistory> findByVoterId(String voterId);
}
