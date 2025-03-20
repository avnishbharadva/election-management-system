package com.ems.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class VoterStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    private String statusDesc;
<<<<<<< HEAD

    @JsonIgnore
    @OneToMany(mappedBy = "voterStatus")
    private List<Voter> voters;
=======
>>>>>>> b0277a2782c5b0b7c4aff00361e9cd7f5828c511
}
