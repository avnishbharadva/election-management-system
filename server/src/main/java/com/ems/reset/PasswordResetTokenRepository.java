//package com.ems.reset;
//
//import com.ems.entities.Officers;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
//    Optional<PasswordResetToken> findByToken(String token);
//    void deleteByUser(Officers officers);
//}
