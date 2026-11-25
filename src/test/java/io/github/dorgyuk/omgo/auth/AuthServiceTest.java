package io.github.dorgyuk.omgo.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import io.github.dorgyuk.omgo.auth.token.JwtProvider;
import io.github.dorgyuk.omgo.auth.token.RefreshTokenRepository;
import io.github.dorgyuk.omgo.auth.token.RefreshTokenService;
import io.github.dorgyuk.omgo.global.config.ClockConfig;
import io.github.dorgyuk.omgo.global.config.PasswordEncoderConfig;
import io.github.dorgyuk.omgo.global.exception.AppException;
import io.github.dorgyuk.omgo.global.exception.ErrorCode;
import io.github.dorgyuk.omgo.user.User;
import io.github.dorgyuk.omgo.user.UserRepository;
import jakarta.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({AuthService.class, JwtProvider.class, RefreshTokenService.class, ClockConfig.class,
	PasswordEncoderConfig.class})
@TestPropertySource(properties = "jwt.secret=test-secret-key-for-auth-service-test-0123456789")
class AuthServiceTest {

	private static final String PASSWORD = "password123";

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private EntityManager entityManager;

	// ===== signup =====

	@Test
	void 가입하면_비밀번호를_해싱해_사용자를_저장한다() {
		String email = uniqueEmail();
		String nickname = uniqueNickname();

		AuthResult result = authService.signup(email, PASSWORD, nickname);
		entityManager.flush();
		entityManager.clear();

		User saved = userRepository.findById(result.user().getId()).orElseThrow();
		assertThat(saved.getEmail()).isEqualTo(email);
		assertThat(saved.getNickname()).isEqualTo(nickname);
		assertThat(saved.getPasswordHash()).isNotEqualTo(PASSWORD);
		assertThat(passwordEncoder.matches(PASSWORD, saved.getPasswordHash())).isTrue();
	}

	@Test
	void 가입하면_가입한_사용자의_토큰을_발급한다() {
		AuthResult result = authService.signup(uniqueEmail(), PASSWORD, uniqueNickname());
		entityManager.flush();

		assertIssuedFor(result, result.user().getId());
	}

	@Test
	void 대소문자만_다른_이메일로_가입하면_EMAIL_ALREADY_EXISTS() {
		String email = uniqueEmail();
		authService.signup(email, PASSWORD, uniqueNickname());

		assertThatThrownBy(() -> authService.signup(email.toUpperCase(), PASSWORD, uniqueNickname()))
			.isInstanceOf(AppException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.EMAIL_ALREADY_EXISTS);
	}

	@Test
	void 대소문자만_다른_닉네임으로_가입하면_NICKNAME_ALREADY_EXISTS() {
		String nickname = uniqueNickname();
		authService.signup(uniqueEmail(), PASSWORD, nickname);

		assertThatThrownBy(() -> authService.signup(uniqueEmail(), PASSWORD, nickname.toUpperCase()))
			.isInstanceOf(AppException.class)
			.extracting("errorCode")
			.isEqualTo(ErrorCode.NICKNAME_ALREADY_EXISTS);
	}

	// ===== helpers =====

	private void assertIssuedFor(AuthResult result, Long userId) {
		assertThat(jwtProvider.parse(result.accessToken()).userId()).isEqualTo(userId);
		assertThat(result.refreshToken()).isNotBlank();
		assertThat(refreshTokenRepository.findAll())
			.anyMatch(token -> token.getUser().getId().equals(userId));
	}

	private static String uniqueEmail() {
		return "auth-" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
	}

	private static String uniqueNickname() {
		return "u" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
	}
}
