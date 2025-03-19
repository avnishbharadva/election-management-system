package com.ems.repositories;

import com.ems.entities.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

    boolean existsByPartyNameOrPartyAbbreviationOrPartyLeaderName(String partyName,String partyAbbreviation,String partyLeaderName);
}
