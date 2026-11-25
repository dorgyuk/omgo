package io.github.dorgyuk.omgo.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.dorgyuk.omgo.auth.token.JwtProvider;
import io.github.dorgyuk.omgo.auth.token.RefreshTokenService;
import io.github.dorgyuk.omgo.global.exception.AppException;
import io.github.dorgyuk.omgo.global.exception.ErrorCode;
import io.github.dorgyuk.omgo.user.User;
import io.github.dorgyuk.omgo.user.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * 이메일·비밀번호 가입과 로그인을 처리하고 토큰을 발급한다.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;

	@Transactional
	public AuthResult signup(String email, String password, String nickname) {
		// 이메일 중복 검사
		if (userRepository.existsByEmailIgnoreCase(email)) {
			throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
		// 닉네임 중복 검사
		if (userRepository.existsByNicknameIgnoreCase(nickname)) {
			throw new AppException(ErrorCode.NICKNAME_ALREADY_EXISTS);
		}

		// 비밀번호 해싱 후 DB에 저장
		String passwordHash = passwordEncoder.encode(password);
		User user = new User(email, passwordHash, nickname);
		userRepository.save(user);

		String accessToken = jwtProvider.createAccessToken(user.getId());
		String refreshToken = refreshTokenService.issue(user);

		return new AuthResult(user, accessToken, refreshToken);
	}

	// TODO: TDD로 구현
	public AuthResult login(String email, String password) {
		throw new UnsupportedOperationException("아직 구현하지 않았습니다.");
	}
}
