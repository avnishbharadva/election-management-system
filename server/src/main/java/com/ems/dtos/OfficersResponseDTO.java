package com.ems.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficersResponseDTO {
    private Long officerId;
    private String ssnNumber;
    private String role;
    private String email;
    private String password;
}
