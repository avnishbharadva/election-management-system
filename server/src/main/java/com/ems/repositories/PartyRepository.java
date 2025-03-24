package com.ems.repositories;

import com.ems.entities.Party;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    boolean existsByPartyNameOrPartyAbbreviationOrPartyLeaderName(String partyName,String partyAbbreviation,String partyLeaderName);

    Optional<Party> findByPartyName(String party);
}
