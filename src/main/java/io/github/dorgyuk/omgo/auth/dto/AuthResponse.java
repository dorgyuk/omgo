package io.github.dorgyuk.omgo.auth.dto;

// @formatter:off
public record AuthResponse(
	String accessToken,

	String tokenType,

	long expiresIn,

	UserInfo user
) {
	public record UserInfo(Long id, String nickname) {}
}
// @formatter:on