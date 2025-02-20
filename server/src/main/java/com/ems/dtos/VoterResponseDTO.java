package com.ems.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoterResponseDTO {
    private String voterId;
    private String firstName;
    private String middleName;
    private String lastName;
}
