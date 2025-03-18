package com.ems.entities;

import com.ems.entities.constants.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String middleName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(unique = true, nullable = false, length = 100)
    private String countyName;

    @Column(length = 9, unique = true, nullable = false)
    private String ssnNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role = RoleType.COUNTY;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;
}
