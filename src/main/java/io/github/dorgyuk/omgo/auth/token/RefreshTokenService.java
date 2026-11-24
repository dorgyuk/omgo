package io.github.dorgyuk.omgo.auth.token;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

import org.springframework.stereotype.Service;

import io.github.dorgyuk.omgo.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	public static final Duration REFRESH_TOKEN_VALIDITY = Duration.ofDays(7);

	private final RefreshTokenRepository refreshTokenRepository;
	private final Clock clock;
	private final SecureRandom random = new SecureRandom();

	public String issue(User user) {

		byte[] bytes = new byte[32];
		random.nextBytes(bytes);

		String rawToken = Base64.getUrlEncoder()
			.withoutPadding()
			.encodeToString(bytes);

		String tokenHash = hash(rawToken);
		Instant expiresAt = clock.instant().plus(REFRESH_TOKEN_VALIDITY);

		refreshTokenRepository.save(new RefreshToken(user, tokenHash, expiresAt));
		return rawToken;
	}

	private String hash(String token) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			byte[] hash = md.digest(token.getBytes(StandardCharsets.UTF_8));

			return HexFormat.of().formatHex(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("sha-256을 사용할 수 없습니다.", e);
		}
	}
}
