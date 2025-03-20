package com.ems.repositories;

<<<<<<< HEAD
import com.ems.dtos.VoterDTO;
import com.ems.dtos.VoterResponseDTO;
import com.ems.entities.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ems.projections.VoterView;
=======
import com.ems.entities.Voter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
<<<<<<< HEAD
import java.util.Optional;
=======
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511

@Repository
public interface VoterRepository extends JpaRepository<Voter, String> {

    boolean existsBySsnNumber(String ssnNumber);
<<<<<<< HEAD


    @Query("SELECT v FROM Voter v LEFT JOIN v.address a WHERE " +
=======
    boolean existsByDmvNumber(String dmvNumber);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);


    @Query("SELECT v FROM Voter v LEFT JOIN v.residentialAddress a WHERE " +
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
            "(:firstName IS NULL OR LOWER(v.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) " +
            "AND (:lastName IS NULL OR LOWER(v.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) " +
            "AND (:dateOfBirth IS NULL OR v.dateOfBirth = :dateOfBirth) " +
            "AND (:dmvNumber IS NULL OR v.dmvNumber = :dmvNumber) " +
            "AND (:ssnNumber IS NULL OR v.ssnNumber = :ssnNumber) " +
            "AND (:city IS NULL OR LOWER(a.city) LIKE LOWER(CONCAT('%', :city, '%')))")
    Page<Voter> searchVoters(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("dateOfBirth") LocalDate dateOfBirth,
            @Param("dmvNumber") String dmvNumber,
            @Param("ssnNumber") String ssnNumber,
            @Param("city") String city,
            Pageable pageable);

<<<<<<< HEAD
    Optional<VoterResponseDTO> findBySsnNumber(String ssnNumber);
    <T> T findByFirstName(String firstName, Class<T> type);
=======
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
