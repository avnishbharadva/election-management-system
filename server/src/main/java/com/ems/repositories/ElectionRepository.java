package com.ems.repositories;

import com.ems.entities.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
