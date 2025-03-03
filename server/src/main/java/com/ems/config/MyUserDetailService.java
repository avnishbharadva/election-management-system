package com.ems.config;

import com.ems.entities.Officers;
import com.ems.repositories.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private RoleRepository myUserRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return myUserRepository.findByEmail(email)
				.map(user -> User.builder()
						.username(user.getEmail())
						.password(user.getPassword())
						.roles(getRoles(user))
						.build())
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	private String[] getRoles(Officers user) {
		log.info("role : {}" , user.getRole().name());
		return new String[]{user.getRole().name()};
	}

}