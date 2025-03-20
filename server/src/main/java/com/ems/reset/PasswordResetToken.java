//package com.ems.reset;
//
//import com.ems.entities.Officers;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.Instant;
//import java.util.Date;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class PasswordResetToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String token;
//
//    @OneToOne
//    @JoinColumn(name = "officer_id", nullable = false)
//    private Officers officer;
//
//    @Column(nullable = false)
//    private Date expiryDate;
//}
