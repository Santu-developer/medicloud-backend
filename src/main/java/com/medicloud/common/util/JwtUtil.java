package com.medicloud.common.util;

public final class JwtUtil {
	private JwtUtil() {}

	public static String bearerToken(String authorizationHeader) {
		if (authorizationHeader == null) return null;
		if (!authorizationHeader.startsWith("Bearer ")) return null;
		return authorizationHeader.substring("Bearer ".length()).trim();
	}
}

