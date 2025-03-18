package com.ems.repositories;

import com.ems.entities.Officers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfficersRepository extends JpaRepository<Officers, Long> {
    Optional<Officers> findByEmail(String email);

    boolean existsByEmailOrCountyNameOrSsnNumber(String email, String countyName, String ssnNumber);

}
