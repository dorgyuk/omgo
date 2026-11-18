package io.github.dorgyuk.omgo.user;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.dorgyuk.omgo.global.exception.AppException;
import io.github.dorgyuk.omgo.global.exception.ErrorCode;

/**
 * 사용자 등록과 닉네임 중복 검사를 담당한다.
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	/**
	 * 이메일 중복, 닉네임 중복을 확인하고 사용자를 저장한다.
	 *
	 * <p>
	 * @param email 사용자가 확정한 이메일
	 * @param password
	 * @param nickname
	 * @return 저장한 사용자
	 * @throws AppException EMAIL_ALREADY_EXISTS: 이메일이 이미 존재하는 경우
	 * @throws AppException NICKNAME_ALREADY_EXISTS: 닉네임이 이미 존재하는 경우
	 */
	@Transactional
	public User registerWithEmail(String email, String password, String nickname) {
		// 이메일 검증
		if (userRepository.existsByEmailIgnoreCase(email)) {
			throw new AppException(ErrorCode.EMAIL_ALREADY_EXISTS);
		}
		// 닉네임 검증
		if (userRepository.existsByNicknameIgnoreCase(nickname)) {
			throw new AppException(ErrorCode.NICKNAME_ALREADY_EXISTS);
		}
		// 비밀번호 해싱
		String hashedPassword = passwordEncoder.encode(password);

		return userRepository.save(new User(email, hashedPassword, nickname));
	}

	/**
	 * 닉네임 중복을 확인한 뒤 사용자를 저장한다.
	 *
	 * <p>닉네임 형식 검증과 Google 인증이 완료된 입력을 전제로 한다.
	 * 중복 검사와 저장은 하나의 트랜잭션에 참여하지만,
	 * 동시에 들어온 요청 사이의 닉네임 중복까지 보장하지는 않는다.
	 *
	 * @param nickname 사용자가 확정한 닉네임
	 * @param googleId 서버에서 Google 인증으로 확인한 사용자 식별자
	 * @return 저장한 사용자
	 * @throws AppException NICKNAME_ALREADY_EXISTS: 대소문자를 무시하고 같은 닉네임이 이미 있는 경우
	 */
	@Transactional
	public User registerWithGoogle(String nickname, String googleId) {

		if (userRepository.existsByNicknameIgnoreCase(nickname)) {
			throw new AppException(ErrorCode.NICKNAME_ALREADY_EXISTS);
		}

		return userRepository.save(new User(nickname, googleId));
	}
}
