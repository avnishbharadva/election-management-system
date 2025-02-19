package com.ems.jwt;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginForm {
    private String username;
    private String password;
}
