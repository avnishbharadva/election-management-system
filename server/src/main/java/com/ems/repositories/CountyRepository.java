package com.ems.repositories;

import com.ems.entities.County;
import com.ems.entities.NameHistory;
import com.ems.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountyRepository extends JpaRepository<County,Long> {
}
