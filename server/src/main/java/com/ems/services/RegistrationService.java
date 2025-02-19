package com.ems.services;

import com.ems.jwt.LoginForm;

public interface RegistrationService {
    String doAuthenticate(LoginForm loginForm);
}
