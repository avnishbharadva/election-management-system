package com.ems.repositories;

import com.ems.entities.County;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountyRepository extends JpaRepository<County,Long> {
    boolean existsByCountyName(String countyName);

}
