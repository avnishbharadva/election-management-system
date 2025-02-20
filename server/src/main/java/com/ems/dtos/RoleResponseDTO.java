package com.ems.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDTO {
    private Long roleId;
    private String username;
    private String ssnNumber;
    private String role;
    private String email;
    private String password;
}
