package com.ems.services.impls;

import com.ems.config.MyUserDetailService;
import com.ems.entities.Officers;
import com.ems.jwt.JwtService;
import com.ems.repositories.RoleRepository;
import com.ems.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.openapitools.model.LoginForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;


    @Override
    public Map<String, String> doAuthenticate(LoginForm loginForm) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getEmail(), loginForm.getPassword()));

        if (authenticate.isAuthenticated()) {
            Officers user = roleRepository.findByEmail(loginForm.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtService.generateToken(myUserDetailService.loadUserByUsername(user.getEmail()), user.getOfficerId(), user.getRole().name());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return response;

        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }

}
