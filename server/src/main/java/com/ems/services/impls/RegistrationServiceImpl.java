package com.ems.services.impls;

import com.ems.config.MyUserDetailService;
import com.ems.entities.Role;
import com.ems.jwt.JwtService;
import com.ems.jwt.LoginForm;
import com.ems.repositories.RoleRepository;
import com.ems.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final MyUserDetailService myUserDetailService;


    @Override
    public String doAuthenticate(LoginForm loginForm) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));

        if (authenticate.isAuthenticated()) {
            Role user = roleRepository.findByUsername(loginForm.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(user.getUsername()), user.getRoleId(), user.getRole().name());
        } else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }
}
