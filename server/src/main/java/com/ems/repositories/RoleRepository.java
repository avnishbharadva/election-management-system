package com.ems.repositories;

import com.ems.entities.Officers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Officers, Long> {
    Optional<Officers> findByEmail(String email);

    boolean existsByEmail(String email);
}
