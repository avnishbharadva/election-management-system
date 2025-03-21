package com.ems.services;

import org.openapitools.model.AuthResponseDTO;
import org.openapitools.model.LoginForm;

import java.util.Map;

public interface RegistrationService {
    AuthResponseDTO doAuthenticate(LoginForm loginForm);
}
