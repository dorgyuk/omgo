package io.github.dorgyuk.omgo.auth.token;

import java.time.Instant;

public record AccessTokenClaims(
	Long userId,

	Instant expiresAt) {
}
