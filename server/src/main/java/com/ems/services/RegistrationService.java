package com.ems.services;

import org.openapitools.model.LoginForm;

import java.util.Map;

public interface RegistrationService {
    Map<String, String> doAuthenticate(LoginForm loginForm);
}
