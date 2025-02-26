package com.ems.config;

import com.ems.entities.Role;
import com.ems.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

	private final RoleRepository myUserRepository;

	@Override

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return myUserRepository.findByUsername(username)
				.map(user -> User.builder()
						.username(user.getUsername())
						.password(user.getPassword())
						.roles(getRoles(user))
						.build())
				.orElseThrow(() -> new UsernameNotFoundException(username));
	}

	private String[] getRoles(Role user) {
		return new String[]{user.getRole() != null ? user.getRole().name() : "STATE_OFFICER"};
	}
}