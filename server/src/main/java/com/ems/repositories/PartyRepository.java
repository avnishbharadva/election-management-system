package com.ems.repositories;

import com.ems.entities.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<Party, Long> {

<<<<<<< HEAD
=======
    boolean existsByPartyNameOrPartyAbbreviationOrPartyLeaderName(String partyName,String partyAbbreviation,String partyLeaderName);
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
