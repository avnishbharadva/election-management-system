package com.ems.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class CandidateName {

    private String firstName;
    private String middleName;
    private String lastName;

}
