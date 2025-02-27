package com.ems.entities;

import com.ems.entities.constants.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "officers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Officers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officerId;

    @Column(length = 9, unique = true, nullable = false)
    private String ssnNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;
}
