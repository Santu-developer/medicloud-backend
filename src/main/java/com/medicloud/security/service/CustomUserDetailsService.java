package com.medicloud.security.service;

import com.medicloud.modules.user.entity.User;
import com.medicloud.modules.user.repository.UserRepository;
import com.medicloud.superadmin.entity.Admin;
import com.medicloud.superadmin.repository.AdminRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final AdminRepository adminRepository;

	public CustomUserDetailsService(UserRepository userRepository, AdminRepository adminRepository) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		// Check admin table first
		java.util.Optional<Admin> admin = adminRepository.findByEmail(email);
		if (admin.isPresent()) {
			return org.springframework.security.core.userdetails.User
					.withUsername(admin.get().getEmail())
					.password(admin.get().getPassword())
					.authorities("SUPER_ADMIN")
					.build();
		}

		// Then check regular users
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		return org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail())
				.password(user.getPassword())
				.authorities(user.getRole().name())
				.build();
	}
}