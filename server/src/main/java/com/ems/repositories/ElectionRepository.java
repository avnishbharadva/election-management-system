package com.ems.repositories;

import com.ems.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findAllByOrderByElectionDateAsc();
    List<Election> findAllByOrderByElectionDateDesc();
}
