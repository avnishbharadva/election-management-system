package com.ems.repositories;

import com.ems.entities.Address;
import com.ems.entities.constants.AddressType;
import org.openapitools.model.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
//    Optional<Address> findByVoter_VoterIdAndAddressType(String voterId, AddressType addressType);

    @Query("SELECT a FROM Address a WHERE a IN " +
            "(SELECT v.residentialAddress FROM Voter v WHERE v.voterId BETWEEN :startVoterId AND :endVoterId) " +
            "OR a IN " +
            "(SELECT v.mailingAddress FROM Voter v WHERE v.voterId BETWEEN :startVoterId AND :endVoterId)")
    List<Address> findAddressesByVoterIdRange(@Param("startVoterId") String startVoterId, @Param("endVoterId") String endVoterId);

    Optional<Address> findByAddressType(AddressType addressType);

}
