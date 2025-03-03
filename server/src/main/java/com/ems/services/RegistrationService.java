package com.ems.services;

import com.ems.jwt.LoginForm;

import java.util.Map;

public interface RegistrationService {
    Map<String, String> doAuthenticate(LoginForm loginForm);
}
