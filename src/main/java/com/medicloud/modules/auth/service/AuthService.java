package com.medicloud.modules.auth.service;

import com.medicloud.modules.auth.dto.LoginRequest;
import com.medicloud.modules.auth.dto.RegisterRequest;

public interface AuthService {
	void register(RegisterRequest request);
	String login(LoginRequest request);
}

