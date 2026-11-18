package io.github.dorgyuk.omgo.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 사용자 저장과 식별자 및 닉네임 기반 조회를 제공한다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Google 식별자로 사용자를 조회한다.
	 *
	 * @param googleId Google 사용자 식별자
	 * @return 해당 사용자, 없으면 빈 Optional
	 */
	Optional<User> findByGoogleId(String googleId);

	/**
	 * 닉네임을 대소문자 구분 없이 조회한다.
	 *
	 * @param nickname 대소문자를 구분하지 않고 조회할 닉네임
	 * @return 해당 사용자, 없으면 빈 Optional
	 */
	Optional<User> findByNicknameIgnoreCase(String nickname);

	/**
	 * 닉네임을 대소문자 구분 없이 사용하는 사용자가 있는지 확인한다.
	 *
	 * @param nickname 대소문자를 구분하지 않고 중복을 확인할 닉네임
	 * @return 같은 닉네임의 사용자가 존재하면 true
	 */
	boolean existsByNicknameIgnoreCase(String nickname);

	/**
	 * 이메일을 대소문자 구분 없이 사용하는 사용자가 있는지 확인한다.
	 *
	 * @param email 대소문자를 구분하지 않고 중복을 확인할 이메일
	 * @return 같은 이메일의 사용자가 존재하면 true
	 */
	boolean existsByEmailIgnoreCase(String email);
}
