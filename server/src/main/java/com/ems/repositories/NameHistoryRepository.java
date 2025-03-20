package com.ems.repositories;

import com.ems.entities.NameHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NameHistoryRepository extends JpaRepository<NameHistory,Long> {
    List<NameHistory> findByVoterId(String voterId);
}
