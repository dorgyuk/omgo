package io.github.dorgyuk.omgo.auth.token;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

class JwtProviderTest {

	// HS256은 32바이트(256비트) 이상의 키가 필요하다.
	private static final String SECRET = "test-secret-key-for-omgo-jwt-provider-0123456789";

	// 발급·검증 시각을 고정해 테스트 결과가 실행 시점에 따라 달라지지 않게 한다.
	private static final Instant NOW = Instant.parse("2026-11-18T12:00:00Z");
	private final Clock clock = Clock.fixed(NOW, ZoneOffset.UTC);

	private final JwtProvider jwtProvider = new JwtProvider(SECRET, clock);

	@Test
	void 발급한_액세스_토큰에서_사용자_ID를_읽을_수_있다() {
		String token = jwtProvider.createAccessToken(123L);

		AccessTokenClaims claims = jwtProvider.parse(token);

		assertThat(claims.userId()).isEqualTo(123L);
	}

	@Test
	void 액세스_토큰은_발급_시점부터_15분_뒤에_만료된다() {
		String token = jwtProvider.createAccessToken(123L);

		AccessTokenClaims claims = jwtProvider.parse(token);

		assertThat(claims.expiresAt()).isEqualTo(Instant.parse("2026-11-18T12:15:00Z"));
	}
}
