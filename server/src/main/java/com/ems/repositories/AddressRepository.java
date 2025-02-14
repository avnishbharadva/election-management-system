package com.ems.repositories;

import com.ems.entities.Address;
import com.ems.entities.constants.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByVoter_VoterIdAndAddressType(String voterId, AddressType addressType);
}
