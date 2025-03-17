package com.ems.services.impls;

import com.ems.config.MyUserDetailService;
import com.ems.exceptions.IllegalCredentials;
import com.ems.jwt.JwtService;
import com.ems.repositories.RoleRepository;
import com.ems.services.RegistrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.AuthResponseDTO;
import org.openapitools.model.LoginForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;


    @Override
    public AuthResponseDTO doAuthenticate(LoginForm loginForm) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

            var user = roleRepository.findByEmail(loginForm.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(user.getEmail()),
                    user.getOfficerId(),
                    user.getRole().name());

            return new AuthResponseDTO(token, "Login successful");

        } catch (BadCredentialsException e) {
            log.warn("Invalid login credentials for email: {}", loginForm.getEmail());
            throw new IllegalCredentials("Invalid email or password");
        } catch (UsernameNotFoundException e) {
            log.warn("User not found for email: {}", loginForm.getEmail());
            throw new IllegalCredentials("User not found");
        } catch (AuthenticationException e) {
            log.error("Authentication error: {}", e.getMessage());
            throw new IllegalCredentials("Authentication failed");
        }
    }


}
