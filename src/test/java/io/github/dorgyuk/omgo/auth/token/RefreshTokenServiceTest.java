package io.github.dorgyuk.omgo.auth.token;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HexFormat;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import io.github.dorgyuk.omgo.user.User;
import io.github.dorgyuk.omgo.user.UserRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({RefreshTokenService.class, RefreshTokenServiceTest.FixedClockConfig.class})
class RefreshTokenServiceTest {

	// 발급 시각을 고정해 만료 시각을 정확히 비교한다.
	private static final Instant NOW = Instant.parse("2026-11-19T12:00:00Z");

	@TestConfiguration(proxyBeanMethods = false)
	static class FixedClockConfig {

		@Bean
		Clock clock() {
			return Clock.fixed(NOW, ZoneOffset.UTC);
		}
	}

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityManager entityManager;

	private User user;

	@BeforeEach
	void setUp() {
		String suffix = UUID.randomUUID().toString().substring(0, 6);
		user = userRepository.save(new User("rt" + suffix, "test-" + UUID.randomUUID()));
	}

	@Test
	void 발급한_토큰은_원문이_아닌_SHA256_해시로_저장된다() {
		String rawToken = refreshTokenService.issue(user);
		entityManager.flush();
		entityManager.clear();

		// 원문으로는 찾을 수 없고, 해시로만 찾을 수 있어야 한다.
		assertThat(refreshTokenRepository.findByTokenHash(rawToken)).isEmpty();
		RefreshToken saved = refreshTokenRepository.findByTokenHash(sha256Hex(rawToken)).orElseThrow();
		assertThat(saved.getUser().getId()).isEqualTo(user.getId());
	}

	@Test
	void 리프레시_토큰은_발급_시점부터_7일_뒤에_만료된다() {
		String rawToken = refreshTokenService.issue(user);
		entityManager.flush();
		entityManager.clear();

		RefreshToken saved = refreshTokenRepository.findByTokenHash(sha256Hex(rawToken)).orElseThrow();
		assertThat(saved.getExpiresAt()).isEqualTo(Instant.parse("2026-11-26T12:00:00Z"));
	}

	@Test
	void 발급할_때마다_다른_토큰이_발급된다() {
		String first = refreshTokenService.issue(user);
		String second = refreshTokenService.issue(user);

		assertThat(first).isNotBlank();
		assertThat(second).isNotBlank();
		assertThat(first).isNotEqualTo(second);
	}

	// 저장 형식: 원문 UTF-8 바이트의 SHA-256 다이제스트를 소문자 16진수(64자)로 표현한다.
	private static String sha256Hex(String value) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
			return HexFormat.of().formatHex(digest);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}
