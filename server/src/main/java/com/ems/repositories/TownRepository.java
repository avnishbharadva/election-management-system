package com.ems.repositories;

import com.ems.entities.StatusHistory;
import com.ems.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {
    boolean existsByTownName(String townName);

}
