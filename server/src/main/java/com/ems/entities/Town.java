package com.ems.entities;

import lombok.Data;

@Entity
@Data
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long townId;

    @Column(nullable = false, unique = true)
    private String townName;
}
