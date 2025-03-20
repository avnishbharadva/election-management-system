package com.ems.repositories;

import com.ems.entities.Address;
import com.ems.entities.constants.AddressType;
<<<<<<< HEAD
=======
import org.openapitools.model.AddressDTO;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
<<<<<<< HEAD
    Optional<Address> findByVoter_VoterIdAndAddressType(String voterId, AddressType addressType);
=======
//    Optional<Address> findByVoter_VoterIdAndAddressType(String voterId, AddressType addressType);

    Optional<Address> findByAddressType(AddressType addressType);

>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
