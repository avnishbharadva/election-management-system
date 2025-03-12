package com.ems.jwt;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class LoginForm {
    private String email;
    private String password;
}