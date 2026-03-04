package com.medicloud.modules.auth.service.impl;

import com.medicloud.common.exception.BusinessException;
import com.medicloud.modules.auth.dto.LoginRequest;
import com.medicloud.modules.auth.dto.RegisterRequest;
import com.medicloud.modules.auth.service.AuthService;
import com.medicloud.modules.user.entity.Role;
import com.medicloud.modules.user.entity.User;
import com.medicloud.modules.user.repository.UserRepository;
import com.medicloud.security.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService,
						   UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public void register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new BusinessException("Email already exists");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.OWNER);
		user.setShopId(request.getShopId());

		userRepository.save(user);
		log.info("User registered: {}", request.getEmail());
	}

	@Override
	public String login(LoginRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getEmail(),
						request.getPassword()
				)
		);

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new BusinessException("User not found"));

		log.info("User logged in: {}", request.getEmail());
		return jwtService.generateToken(user.getEmail(), user.getShopId(), user.getRole().name());
	}
}

