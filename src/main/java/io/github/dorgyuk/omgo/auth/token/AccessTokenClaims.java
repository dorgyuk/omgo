package io.github.dorgyuk.omgo.auth.token;

import java.time.Instant;

// @formatter:off
public record AccessTokenClaims(
	Long userId,

	Instant expiresAt
) {}
// @formatter:on
