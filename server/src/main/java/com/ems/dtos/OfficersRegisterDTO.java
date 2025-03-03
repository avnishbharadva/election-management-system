package com.ems.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficersRegisterDTO {
    private String ssnNumber;
    private String role;
    private String email;
    private String password;
}
