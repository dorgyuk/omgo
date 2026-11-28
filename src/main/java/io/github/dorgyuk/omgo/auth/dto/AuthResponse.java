package io.github.dorgyuk.omgo.auth.dto;

public record AuthResponse(
// @formatter:off
	String accessToken,
	String tokenType,
	long expiresIn,
	UserInfo user

// @formatter:on
) {
	public record UserInfo(Long id, String nickname) {
	}
}
